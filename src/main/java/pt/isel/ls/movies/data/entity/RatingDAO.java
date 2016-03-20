package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.model.Rating;

import java.sql.Connection;

/**
 * Rating's Data Access Object
 */
public class RatingDAO {

    /**
     * Submit a Movie Rating to the database.
     *
     * @param rating Rating
     * @return Unique identifier of the Rating
     */
    public static int submitRating(Connection connection, Rating rating){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all of a specific movie ratings.
     * @param mid Unique identifier for the movie ratings.
     * @return List of the movie ratings
     */
    public static Rating[] getMovieRatings(Connection connection, int mid){
        throw new UnsupportedOperationException();
    }
}
