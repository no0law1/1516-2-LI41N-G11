package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Review;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class of model class Review Data Access Object
 */
public class ReviewDAOTest {

    public static DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        System.setProperty("LS_1516V_DB_SERVER", "movies_test");
        dataSource = DataSourceFactory.createInstance();
    }

    @After
    public void tearDown() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.prepareStatement("ALTER SEQUENCE movie_id_seq RESTART WITH 1").execute();
        }
    }

    @Test
    public void testSubmitReview() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "KickAss Movie", "review1", 5);

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));

            Review.ReviewUID expectedReviewUID = ReviewDAO.submitReview(connection, expected);
            Review.ReviewUID actualReviewUID = new Review.ReviewUID(1,1);
            assertTrue(expectedReviewUID.equals(actualReviewUID));

            Review actual = ReviewDAO.getReview(connection, 1, 1);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetReviews() throws Exception {
        List<Review> expected = new LinkedList<>();
        expected.add(new Review(1, 1, "Nuno", "Kickass Movie", null, 5));
        expected.add(new Review(1, 2, "Nuno", "Just Another Movie", null, 3));
        expected.add(new Review(1, 3, "Nuno", "Superb Movie", null, 5));
        expected.add(new Review(1, 4, "Nuno", "It's Ok", null, 2));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            int id = MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            for (Review review : expected) {
                ReviewDAO.submitReview(connection, review);
            }

            List<Review> actual = ReviewDAO.getReviews(connection, id);
            assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                assertTrue(expected.get(i).equals(actual.get(i)));
            }
        }

    }

    @Test
    public void testGetReview() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "KickAss Movie", "review1", 5);

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            ReviewDAO.submitReview(connection, expected);

            Review actual = ReviewDAO.getReview(connection, new Review.ReviewUID(1,1));

            assertTrue(expected.equals(actual));
        }
    }
}