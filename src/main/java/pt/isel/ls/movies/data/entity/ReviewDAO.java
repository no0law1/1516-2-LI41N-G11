package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.data.exceptions.NoDataException;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
    public static Review.ReviewUID submitReview(Connection connection, Review review) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into review(mid, id, reviewer_name, review_summary, review, rating) values (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, review.getMid());
        preparedStatement.setInt(2, getReviewId(connection, review.getMid()));
        preparedStatement.setString(3, review.getReviewerName());
        preparedStatement.setString(4, review.getReviewSummary());
        preparedStatement.setString(5, review.getReview());
        preparedStatement.setInt(6, review.getRating());

        if(preparedStatement.executeUpdate() != 0){
            // Update rating count
            RatingDAO.submitRating(connection, new Rating(review.getMid(), review.getRating()));

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return new Review.ReviewUID(resultSet.getInt(1), resultSet.getInt(2));
            }
        }
        throw new InsertException("Cannot insert movie");
    }

    /**
     * Gets all review from a movie from the database
     *
     * @param mid Unique identifier of a Movie
     * @return All reviews of a Movie
     */
    public static List<Review> getReviews(Connection connection, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from review where mid=?");
        preparedStatement.setInt(1, mid);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Review> reviews = new LinkedList<>();
        while(resultSet.next()){
            int id = resultSet.getInt(2);
            String reviewerName = resultSet.getString(3);
            String reviewSummary = resultSet.getString(4);
            String review = null; //resultSet.getString(5);
            int rating = resultSet.getInt(6);
            reviews.add(new Review(mid, id, reviewerName, reviewSummary, review, rating));
        }
        if(reviews.isEmpty()){
            throw new NoDataException("There are no Reviews to Movie");
        }
        return reviews;
    }

    /**
     * Gets the specific review from a specific movie
     *
     * @param connection
     * @param reviewUID
     * @return
     */
    public static Review getReview(Connection connection, Review.ReviewUID reviewUID) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from review where mid=? and id=?");
        preparedStatement.setInt(1, reviewUID.mid);
        preparedStatement.setInt(2, reviewUID.id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            String reviewerName = resultSet.getString(3);
            String reviewSummary = resultSet.getString(4);
            String review = resultSet.getString(5);
            int rating = resultSet.getInt(6);
            return new Review(reviewUID.mid, reviewUID.id, reviewerName, reviewSummary, review, rating);
        }
        throw new NoDataException("There is no Review with mid: " + reviewUID.mid + " and id: " + reviewUID.id);
    }

    /**
     * Gets the specific review from a specific movie
     *
     * @param mid Unique identifier of a Movie
     * @param rid Unique identifier of a Review
     * @return one review
     */
    public static Review getReview(Connection connection, int mid, int rid) throws Exception {
        return getReview(connection, new Review.ReviewUID(mid, rid));
    }

    private static int getReviewId(Connection connection, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select count(*)+1 from review where mid=?");
        preparedStatement.setInt(1, mid);

        try(ResultSet resultSet = preparedStatement.executeQuery()){
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        throw new NoDataException("Movie with that id does not exist");
    }
}
