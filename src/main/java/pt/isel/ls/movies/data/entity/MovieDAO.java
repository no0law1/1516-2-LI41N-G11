package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.exceptions.BadRequestException;
import pt.isel.ls.movies.exceptions.NoDataException;
import pt.isel.ls.movies.model.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Movie's Data Access Object
 */
public class MovieDAO {

    private static Movie map(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String title = resultSet.getString(2);
        int releaseYear = resultSet.getInt(3);
        String genre = resultSet.getString(4);
        float averageRating = resultSet.getFloat(5);
        return new Movie(id, title, releaseYear, genre, averageRating);
    }

    public static int getCount(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select count(*) from movie");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Inserts a movie into the database
     *
     * @param movie movie to be inserted
     * @return unique id
     */
    public static int submitMovie(Connection connection, Movie movie) throws Exception {
        if (movie.getTitle() == null || movie.getReleaseYear() == -1
                || movie.getTitle().isEmpty()) {
            throw new BadRequestException("You missed some parameters");
        }

        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into movie(title, release_year, genre) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, movie.getTitle());
        preparedStatement.setInt(2, movie.getReleaseYear());
        preparedStatement.setString(3, movie.getGenre());

        if(preparedStatement.executeUpdate() != 0){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        throw new InsertException("Cannot insert movie");
    }

    /**
     * Gets from the database all the movies
     *
     * @param connection {@code Connection} to be used to fecth the results
     * @return an array of all movies
     */
    public static List<Movie> getMovies(Connection connection) throws Exception {
        return getMovies(connection, -1, 0, null);
    }

    /**
     * Gets from the database all {@param top} movies from {@param line} line
     *
     * @param connection {@code Connection} to be used to fecth the results
     * @param top number of rows, -1 if all
     * @param skip number of rows to skip
     * @return an array of {@param top} movies from {@param line} line
     * @throws Exception
     */
    public static List<Movie> getMovies(Connection connection, int top, int skip) throws Exception {
        return getMovies(connection, top, skip, null);
    }

    public static List<Movie> getMovies(Connection connection, int top, int skip, Movie.Sort sort) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery(sort));
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Movie> movies = new LinkedList<>();
        //resultSet.absolute(skip);
        for (int i=0; i<skip; i++) if(!resultSet.next()) return movies;
        for (int i=0; resultSet.next() && (top < 0 || i < top); i++) {
            Movie movie = map(resultSet);
            movies.add(movie);
        }
        return movies;
    }

    private static String getQuery(Movie.Sort sort){
        StringBuilder query = new StringBuilder("select movie.*, COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating from movie LEFT JOIN rating ON id=mid GROUP BY id ");
        if(sort == null){
            sort = Movie.Sort.ADDED_DATE;
        }
        switch(sort){
            case ADDED_DATE: return query.append(" ORDER BY id ASC").toString();
            case ADDED_DATE_DESC: return query.append(" ORDER BY id DESC").toString();
            case YEAR: return query.append(" ORDER BY release_year ASC").toString();
            case YEAR_DESC: return query.append(" ORDER BY release_year DESC").toString();
            case TITLE: return query.append(" ORDER BY title ASC").toString();
            case TITLE_DESC: return query.append(" ORDER BY title DESC").toString();
            case RATING: return query.append(" ORDER BY averageRating ASC").toString();
            case RATING_DESC: return query.append(" ORDER BY averageRating DESC").toString();
        }

        throw new IllegalArgumentException("invalid sort parameter");
    }

    /**
     * Gets from the database a specific movie
     *
     * @param id unique id of the movie
     * @return the movie with the unique id {{@code id}}
     */
    public static Movie getMovie(Connection connection, int id) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select movie.*, COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating from movie LEFT JOIN rating ON id=mid WHERE id=? GROUP BY movie.id");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return map(resultSet);
        }
        throw new NoDataException("There is no such movie with the id: " + id);
    }

    /**
     * Gets from the database the movie with the highest average rating.
     *
     * @return the detail for the movie with the highest average rating.
     */
    public static Movie getHighestRatingMovie(Connection connection) throws Exception {
        return getHighestRatingMovies(connection, 1).get(0);
    }

    /**
     * Gets from the database the movie with the lowest average rating.
     *
     * @return the detail for the movie with the lowest average rating.
     */
    public static Movie getLowestRatingMovie(Connection connection) throws Exception {
        return getLowestRatingMovies(connection, 1).get(0);
    }

    /**
     * Gets from the database the {@code n} movies with the highest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the highest average rating.
     */
    public static List<Movie> getHighestRatingMovies(Connection connection, int n) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select id, title, release_year, genre, COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating \n" +
                        "\tfrom movie left join rating on id=mid\n" +
                        "\tgroup by id\n" +
                        "\torder by averageRating desc, release_year desc, title asc\n" +
                        "\tlimit ?");
        preparedStatement.setInt(1, n);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Movie> movies = new LinkedList<>();
        while(resultSet.next()){
            Movie movie = map(resultSet);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Gets from the database the {@code n} movies with the lowest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static List<Movie> getLowestRatingMovies(Connection connection, int n) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select id, title, release_year, genre, COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating \n" +
                        "\tfrom movie left join rating on id=mid\n" +
                        "\tgroup by id\n" +
                        "\torder by averageRating asc, release_year desc, title asc\n" +
                        "\tlimit ?");
        preparedStatement.setInt(1, n);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Movie> movies = new LinkedList<>();
        while(resultSet.next()){
            Movie movie = map(resultSet);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Gets from the database the movie with the most reviews.
     *
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static Movie getMostReviewedMovie(Connection connection) throws Exception {
        return getMostReviewedMovies(connection, 1).get(0);
    }

    private static List<Movie> getReviewedMovies(Connection connection, int n, boolean descNotAsc) throws Exception {
        String sorting = descNotAsc ? "desc" : "asc";
        PreparedStatement preparedStatement =
                connection.prepareStatement("select movie.id, title, release_year, genre, COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating, COALESCE(count(review.mid), 0) AS reviews\n" +
                        "\tfrom movie left join review on movie.id=review.mid left join rating on movie.id=rating.mid\n" +
                        "\tgroup by movie.id\n" +
                        "\torder by reviews " + sorting + ", release_year " + sorting + ", title " + sorting + "\n" +
                        "\tlimit ?");
        preparedStatement.setInt(1, n);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Movie> movies = new LinkedList<>();
        while (resultSet.next()) {
            Movie movie = map(resultSet);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Gets from the database the {@code n} movies with the most reviews.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static List<Movie> getMostReviewedMovies(Connection connection, int n) throws Exception {
        return getReviewedMovies(connection, n, true);
    }

    /**
     * Gets from the database the {@code n} movies with the most reviews.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static List<Movie> getLeastReviewedMovies(Connection connection, int n) throws Exception {
        return getReviewedMovies(connection, n, false);
    }

    public static List<Movie> getCollectionMovies(Connection connection, int cid) throws SQLException {
        return getCollectionMovies(connection, cid, -1, 0);
    }

    public static List<Movie> getCollectionMovies(Connection connection, int cid, int top, int skip) throws SQLException {
        List<Movie> movies = new LinkedList<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement("select movie.* , COALESCE(SUM(val*count)::float/SUM(count), 0) AS averageRating\n"
                        + "from movie_collection join movie on mid=movie.id\n"
                        + "left join review on movie.id=review.mid\n"
                        + "left join rating on movie.id=rating.mid\n"
                        + "where cid=?\n"
                        + "group by movie.id\n"
                        + "order by movie.id");
        preparedStatement.setInt(1, cid);
        ResultSet resultSet = preparedStatement.executeQuery();
        for (int i=0; i<skip; i++) if(!resultSet.next()) return movies;
        for (int i=0; resultSet.next() && (top < 0 || i < top); i++) {
            Movie movie = map(resultSet);
            movies.add(movie);
        }
        return movies;
    }

}
