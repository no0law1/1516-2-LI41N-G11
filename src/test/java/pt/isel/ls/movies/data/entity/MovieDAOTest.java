package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Rating;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class of model class Movie Data Access Object
 */
public class MovieDAOTest {

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
    public void testInsertMovie() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            Movie expected = new Movie(1, "test", 2016, "genre_test");
            int id = MovieDAO.submitMovie(connection, expected);
            Movie actual = MovieDAO.getMovie(connection, id);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetMovies() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> actual = new LinkedList<>();
            actual.add(new Movie(1, "test1", 2016, "genre_test"));
            actual.add(new Movie(2, "test2", 2016, "genre_test"));
            actual.add(new Movie(3, "test3", 2016, "genre_test"));
            for (Movie movie : actual) {
                MovieDAO.submitMovie(connection, movie);
            }
            List<Movie> expected = MovieDAO.getMovies(connection);


            assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                assertTrue(expected.get(i).equals(actual.get(i)));
            }
        }
    }

    @Test
    public void testGetMovie() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            Movie expected = new Movie(2, "test2", 2016, "genre_test2");
            int id = MovieDAO.submitMovie(connection, expected);
            Movie actual = MovieDAO.getMovie(connection, id);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetHighestRatingMovie() throws Exception {
        Movie expected = new Movie(1, "test1", 2016, "genre1");
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, expected);
            MovieDAO.submitMovie(connection, new Movie(2, "test2", 2016, "genre1"));
            RatingDAO.submitRating(connection, new Rating(1, 4, 2));

            Movie actual = MovieDAO.getHighestRatingMovie(connection);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetHighestRatingMovieWithSeveralRatings() throws Exception {
        Movie expected = new Movie(2, "test2", 2016, "genre1");
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test1", 2016, "genre1"));
            MovieDAO.submitMovie(connection, expected);
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));
            RatingDAO.submitRating(connection, new Rating(2, 5));

            Movie actual = MovieDAO.getHighestRatingMovie(connection);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetLowestRatingMovieWithNoRating() throws Exception {
        Movie expected = new Movie(1, "test1", 2016, "genre1");
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, expected);
            MovieDAO.submitMovie(connection, new Movie(2, "test2", 2016, "genre1"));
            RatingDAO.submitRating(connection, new Rating(1, 4, 1));

            Movie actual = MovieDAO.getLowestRatingMovie(connection);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetLowestRatingMovieWithSeveralRatings() throws Exception {
        Movie expected = new Movie(1, "test1", 2016, "genre1");
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, expected);
            MovieDAO.submitMovie(connection, new Movie(2, "test2", 2016, "genre1"));
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));
            RatingDAO.submitRating(connection, new Rating(2, 5));

            Movie actual = MovieDAO.getLowestRatingMovie(connection);
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testGetHighestRatingMovies() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));

            List<Movie> actual = MovieDAO.getHighestRatingMovies(connection, 2);


            assertEquals(actual.size(), 2);
            assertTrue(actual.get(0).equals(expected.get(1)));
            assertTrue(actual.get(1).equals(expected.get(0)));
        }
    }

    @Test
    public void testGetHighestRatingMoviesWithMoreLengthThanMovies() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));
            RatingDAO.submitRating(connection, new Rating(3, 1));

            List<Movie> actual = MovieDAO.getHighestRatingMovies(connection, 5);


            assertEquals(actual.size(), 3);
            assertTrue(actual.get(0).equals(expected.get(1)));
            assertTrue(actual.get(1).equals(expected.get(0)));
            assertTrue(actual.get(2).equals(expected.get(2)));
        }
    }

    @Test
    public void testGetHighestRatingMoviesWithNoRating() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));

            List<Movie> actual = MovieDAO.getHighestRatingMovies(connection, 5);


            assertEquals(actual.size(), 2);
            assertTrue(actual.get(0).equals(expected.get(1)));
            assertTrue(actual.get(1).equals(expected.get(0)));
        }
    }

    @Test
    public void testGetLowestRatingMovies() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));

            List<Movie> actual = MovieDAO.getLowestRatingMovies(connection, 2);


            assertEquals(actual.size(), 2);
            assertTrue(actual.get(0).equals(expected.get(0)));
            assertTrue(actual.get(1).equals(expected.get(1)));
        }
    }

    @Test
    public void testGetLowestRatingMoviesWithMoreLengthThanMovies() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));
            RatingDAO.submitRating(connection, new Rating(3, 1));

            List<Movie> actual = MovieDAO.getLowestRatingMovies(connection, 5);


            assertEquals(actual.size(), 3);
            assertTrue(actual.get(0).equals(expected.get(2)));
            assertTrue(actual.get(1).equals(expected.get(0)));
            assertTrue(actual.get(2).equals(expected.get(1)));
        }
    }

    @Test
    public void testGetLowestRatingMoviesWithNoRating() throws Exception {
        List<Movie> expected = new LinkedList<>();
        expected.add(new Movie(1, "test1", 2016, "genre_test"));
        expected.add(new Movie(2, "test2", 2016, "genre_test"));
        expected.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            RatingDAO.submitRating(connection, new Rating(1, 4));
            RatingDAO.submitRating(connection, new Rating(1, 1));
            RatingDAO.submitRating(connection, new Rating(2, 4));

            List<Movie> actual = MovieDAO.getLowestRatingMovies(connection, 5);


            assertEquals(actual.size(), 2);
            assertTrue(actual.get(0).equals(expected.get(0)));
            assertTrue(actual.get(1).equals(expected.get(1)));
        }
    }
}