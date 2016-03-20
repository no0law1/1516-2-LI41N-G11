package pt.isel.ls.movies.data;

import pt.isel.ls.movies.model.Movie;

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
    public static int insertMovie(Movie movie){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database all the movies
     * @return an array of all movies
     */
    public static Movie[] getMovies(){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database a specific movie
     *
     * @param id unique id of the movie
     * @return the movie with the unique id {{@code id}}
     */
    public static Movie getMovie(int id){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the movie with the highest average rating.
     *
     * @return the detail for the movie with the highest average rating.
     */
    public static Movie getHighestRatingMovie(){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the movie with the lowes average rating.
     *
     * @return the detail for the movie with the lowes average rating.
     */
    public static Movie getLowestRatingMovie(){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the {@code n} movies with the highest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the highest average rating.
     */
    public static Movie[] getHighestRatingMovies(int n){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets from the database the {@code n} movie with the lowest average rating.
     *
     * @param n number of movies
     * @return the detail for the {@code n} movies with the lowest average rating.
     */
    public static Movie[] getLowestRatingMovies(int n){
        throw new UnsupportedOperationException();
    }
}
