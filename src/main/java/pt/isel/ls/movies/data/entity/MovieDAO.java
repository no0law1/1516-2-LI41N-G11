package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.data.exceptions.NoDataException;
import pt.isel.ls.movies.model.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Movie's Data Access Object
 */
public class MovieDAO {

    /**
     * Inserts a movie into the database
     *
     * @param movie movie to be inserted
     * @return unique id
     */
    public static int submitMovie(Connection connection, Movie movie) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into movie(title, release_year, genre) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, movie.title);
        preparedStatement.setInt(2, movie.releaseYear);
        preparedStatement.setString(3, movie.genre);

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
     * @return an array of all movies
     */
    public static List<Movie> getMovies(Connection connection) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from movie");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Movie> movies = new LinkedList<>();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            int releaseYear = resultSet.getInt(3);
            String genre = resultSet.getString(4);
            Movie movie = new Movie(id, title, releaseYear, genre);
            movies.add(movie);
        }
        if(!movies.isEmpty()) {
            return movies;
        }
        throw new NoDataException();
    }

    /**
     * Gets from the database a specific movie
     *
     * @param id unique id of the movie
     * @return the movie with the unique id {{@code id}}
     */
    public static Movie getMovie(Connection connection, int id) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from movie where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String title = resultSet.getString(2);
            int releaseYear = resultSet.getInt(3);
            String genre = resultSet.getString(4);
            return new Movie(id, title, releaseYear, genre);
        }
        throw new NoDataException();
    }

    /**
     * Gets from the database the movie with the highest average rating.
     *
     * @return the detail for the movie with the highest average rating.
     */
    public static Movie getHighestRatingMovie(Connection connection) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from movie where id=?");
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            int releaseYear = resultSet.getInt(3);
            String genre = resultSet.getString(4);
            return new Movie(id, title, releaseYear, genre);
        }
        throw new NoDataException();
    }

    /**
     * Gets from the database the movie with the lowes average rating.
     *
     * @return the detail for the movie with the lowes average rating.
     */
    public static Movie getLowestRatingMovie(Connection connection){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the {@code n} movies with the highest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the highest average rating.
     */
    public static Movie[] getHighestRatingMovies(Connection connection, int n){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the {@code n} movie with the lowest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static Movie[] getLowestRatingMovies(Connection connection, int n){
        throw new UnsupportedOperationException();
    }
}
