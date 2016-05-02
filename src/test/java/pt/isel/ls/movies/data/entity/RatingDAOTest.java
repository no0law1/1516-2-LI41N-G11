package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Rating;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class of model class {@link RatingDAO}
 */
public class RatingDAOTest {

    public static DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = DataSourceFactory.createTestInstance();
    }

    @After
    public void tearDown() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.prepareStatement("ALTER SEQUENCE movie_id_seq RESTART WITH 1").execute();
        }
    }

    @Test
    public void testSubmitNonExistingRating() throws Exception {
        Rating expected = new Rating(1, 1, 1);

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            int id = MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));

            assertEquals(expected.getMid(), id);
            assertEquals(expected, RatingDAO.submitRating(connection, expected));
        }
    }

    @Test
    public void testSubmitExistingRating() throws Exception {
        Rating expected = new Rating(1, 1, 2);

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            int id = MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            RatingDAO.submitRating(connection, expected);

            assertEquals(expected.getMid(), id);
            assertEquals(expected, RatingDAO.submitRating(connection, expected));
        }
    }

    @Test(expected = SQLException.class)
    public void testSubmitBadRating() throws Exception {
        Rating expected = new Rating(1, 7, 2);

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            RatingDAO.submitRating(connection, expected);
        }
    }

    @Test
    public void testGetMovieRatings() throws Exception {
        List<Rating> expected = new LinkedList<>();
        expected.add(new Rating(1, 1, 1));
        expected.add(new Rating(1, 2, 1));
        expected.add(new Rating(1, 3, 1));
        expected.add(new Rating(1, 4, 1));
        expected.add(new Rating(1, 5, 1));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            int id = MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            for (Rating rating : expected) {
                RatingDAO.submitRating(connection, rating);
            }

            List<Rating> actual = RatingDAO.getMovieRatings(connection, id);
            assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i), actual.get(i));
            }
        }
    }
}