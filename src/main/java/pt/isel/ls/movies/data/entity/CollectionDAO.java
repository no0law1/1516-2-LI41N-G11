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
     * Retrieves all the collections from the database
     *
     * @param connection connection to the database
     * @return a list of all the collections
     * @throws Exception if there is no data, or an error to the connection
     */
    public static List<Collection> getCollections(Connection connection) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from collection");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Collection> collections = new LinkedList<>();
        while (resultSet.next()) {
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

}
