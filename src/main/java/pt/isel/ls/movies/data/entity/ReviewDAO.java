package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.model.Review;

import java.sql.Connection;

/**
 * Review's Data Access Object
 */
public class ReviewDAO {

    /**
     * Submit a review of a specific movie to the database
     *
     * @param review Review to be added
     * @return Unique identifier for the review added
     */
    public static int[] submitReview(Connection connection, Review review){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all review from a movie from the database
     *
     * @param mid Unique identifier of a Movie
     * @return All reviews of a Movie
     */
    public static Review[] getReviews(Connection connection, int mid){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the specific review from a specific movie
     *
     * @param mid Unique identifier of a Movie
     * @param rid Unique identifier of a Review
     * @return one review
     */
    public static Review getReview(Connection connection, int mid, int rid){
        throw new UnsupportedOperationException();
    }
}
