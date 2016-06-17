package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.exceptions.BadRequestException;
import pt.isel.ls.movies.exceptions.NoDataException;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.model.Review;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * review's Data Access Object
 */
public class ReviewDAO {

    private static Review map(ResultSet resultSet) throws SQLException {
        int mid = resultSet.getInt(1);
        int id = resultSet.getInt(2);
        String reviewerName = resultSet.getString(3);
        String reviewSummary = resultSet.getString(4);
        String review = resultSet.getString(5);
        int rating = resultSet.getInt(6);
        return new Review(mid, id, reviewerName, reviewSummary, review, rating);
    }

    public static int getCount(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select count(*) from review");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public static int getCount(Connection connection, int mid) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select count(*) from review WHERE mid = ?");
        preparedStatement.setInt(1, mid);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Submit a review of a specific movie to the database
     *
     * @param review review to be added
     * @return Unique identifier for the review added
     */
    public static Review.ReviewUID submitReview(Connection connection, Review review) throws Exception {
        if (review.getReviewerName() == null
                || review.getReviewSummary() == null
                || review.getReview() == null
                || review.getRating() == -1) {
            throw new BadRequestException("You missed some parameters");
        }

        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into review(mid, id, reviewer_name, review_summary, review, rating) values (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, review.getMid());
        preparedStatement.setInt(2, getNextReviewId(connection, review.getMid()));
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
        return getReviews(connection, mid, -1, 0);
    }

    /**
     * Gets all review from a movie from the database
     *
     * @param mid Unique identifier of a Movie
     * @param top number of rows, -1 if all
     * @param skip number of rows to skip
     * @return All reviews of a Movie
     */
    public static List<Review> getReviews(Connection connection, int mid, int top, int skip) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from review where mid=?");
        preparedStatement.setInt(1, mid);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Review> reviews = new LinkedList<>();
        for (int i=0; i<skip; i++) if(!resultSet.next()) return reviews;
        for (int i=0; resultSet.next() && (top < 0 || i < top); i++){
            Review review = map(resultSet);
            review.setReview(null);
            reviews.add(review);
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
            Review review = map(resultSet);
            return review;
        }
        throw new NoDataException("There is no review with mid: " + reviewUID.mid + " and id: " + reviewUID.id);
    }

    /**
     * Gets the specific review from a specific movie
     *
     * @param mid Unique identifier of a Movie
     * @param rid Unique identifier of a review
     * @return one review
     */
    public static Review getReview(Connection connection, int mid, int rid) throws Exception {
        return getReview(connection, new Review.ReviewUID(mid, rid));
    }

    private static int getNextReviewId(Connection connection, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select max(id) from review where mid=?");
        preparedStatement.setInt(1, mid);

        try(ResultSet resultSet = preparedStatement.executeQuery()){
            if(resultSet.next()){
                return resultSet.getInt(1)+1;
            }
        }
        throw new NoDataException("Movie with that id " + mid + " does not exist");
    }
}
