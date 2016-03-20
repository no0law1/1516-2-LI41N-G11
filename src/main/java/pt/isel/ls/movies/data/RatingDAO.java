package pt.isel.ls.movies.data;

import pt.isel.ls.movies.model.Rating;

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
    public static int submitRating(Rating rating){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all of a specific movie ratings.
     * @param mid Unique identifier for the movie ratings.
     * @return List of the movie ratings
     */
    public static Rating[] getMovieRatings(int mid){
        throw new UnsupportedOperationException();
    }
}
