package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.data.exceptions.NoDataException;
import pt.isel.ls.movies.model.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection's Data Access Object
 */
public class CollectionDAO {

    /**
     * Inserts a new {@link Collection} to the database
     *
     * @param connection connection to the database
     * @param collection collection to be inserted
     * @return if success, unique id, otherwise throws exception
     * @throws Exception if it was not possible to insert, or an error to the connection
     */
    public static int createCollection(Connection connection, Collection collection) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into collection(name, description) values (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, collection.getName());
        preparedStatement.setString(2, collection.getDescription());

        if (preparedStatement.executeUpdate() != 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        throw new InsertException("Cannot insert collection");
    }

    /**
     * Retrieves a {@link Collection} from the database with the specified {@code id}
     *
     * @param connection connection to the database
     * @param id         id of the specified collection
     * @return the collection with th {@code id} specified
     * @throws Exception if there is no data, or an error to the connection
     */
    public static Collection getCollection(Connection connection, int id) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from collection where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            return new Collection(id, name, description);
        }
        throw new NoDataException("There is no such collection with the id: " + id);
    }

    /**
     * Retrieves a {@link Collection} from the database with the specified {@code id}
     *
     * @param connection connection to the database
     * @param name         name of the specified collection
     * @return the collection with th {@code id} specified
     * @throws Exception if there is no data, or an error to the connection
     */
    public static Collection getCollection(Connection connection, String name) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select id, name, description from collection where name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Collection(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }
        throw new NoDataException("There is no such collection with the name: " + name);
    }

    /**
     * Retrieves all the collections from the database
     *
     * @param connection connection to the database
     * @return a list of all the collections
     * @throws Exception if there is no data, or an error to the connection
     */
    public static List<Collection> getCollections(Connection connection) throws Exception {
        return getCollections(connection, -1, 0);
    }

    public static List<Collection> getCollections(Connection connection, int top, int skip) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from collection ORDER BY id ASC");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Collection> collections = new LinkedList<>();
        //resultSet.absolute(skip);
        for (int i=0; i<skip; i++) if(!resultSet.next()) throw new NoDataException("There are no collection");
        for (int i=0; resultSet.next() && (top < 0 || i < top); i++) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            collections.add(new Collection(id, name, description));
        }
        if (!collections.isEmpty()) {
            return collections;
        }
        throw new NoDataException("There are no collection");
    }

    public static Collection.MovieCollectionUID postMovieToCollection(Connection connection, int cid, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into movie_collection(cid, mid) values (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, cid);
        preparedStatement.setInt(2, mid);

        if (preparedStatement.executeUpdate() != 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return new Collection.MovieCollectionUID(resultSet.getInt(1), resultSet.getInt(2));
            }
        }
        throw new InsertException("Cannot insert movie with id:" + mid + "to collection with id:" + cid);
    }

    public static boolean removeMovieFromCollection(Connection connection, int cid, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("delete from movie_collection where cid = ? and mid = ?");
        preparedStatement.setInt(1, cid);
        preparedStatement.setInt(2, mid);

        if (preparedStatement.executeUpdate() != 0) {
            return true;
        }
        throw new NoDataException("There was no data to delete");
    }

    public static List<Integer> getMovies(Connection connection, int cid) throws Exception{
        PreparedStatement preparedStatement = connection.prepareStatement("select mid from movie_collection where cid=?");
        preparedStatement.setInt(1, cid);

        List<Integer> mids = new LinkedList<>();

        try(ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                mids.add(resultSet.getInt(1));
            }
        }
        return mids;
    }

    /**
     * Creates a collection with the movies reviewed by {@param reviewerName}, if exists
     * In case of a collection with the name of a reviewer already exists the new movies reviewed by
     * {@param reviewerName} will be added
     *
     * @param connection
     * @param reviewerName
     * @return the created collection unique id
     * @throws Exception if it was not possible to insert, or an error with the connection occurs
     */
    public static int createReviewerCollection(Connection connection, String reviewerName) throws Exception{
        List<Integer> revmids = ReviewDAO.getMoviesReviewedBy(connection, reviewerName);
        if(revmids.isEmpty()) throw new InsertException("No movies for the given reviewer");

        int cid;
        List<Integer> collmids;
        try {
            cid = CollectionDAO.getCollection(connection, reviewerName).getId();
            collmids = getMovies(connection, cid);
        }
        catch(NoDataException ex) {
            cid = createCollection(connection, new Collection(reviewerName, "A collection with the movies reviewed by: " + reviewerName));
            collmids = null;
        }

        for (Integer mid: revmids ) {
            if(collmids == null || !collmids.contains(mid)) postMovieToCollection(connection, cid, mid);
        }

        return cid;
    }

}
