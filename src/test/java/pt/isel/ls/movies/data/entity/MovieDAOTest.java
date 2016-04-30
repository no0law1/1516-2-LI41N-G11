package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.data.exceptions.NoDataException;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.model.Review;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class of model class {@link MovieDAO}
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

    @Test(expected = SQLException.class)
    public void testInsertionErrorOnMovieWithTheSameTitleAndReleaseYear() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test", 2016, "genre_test"));
            MovieDAO.submitMovie(connection, new Movie(2, "test", 2016, "genre_test"));
        }
    }

    @Test
    public void testSubmitMovie() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            Movie expected = new Movie(1, "test", 2016, "genre_test");
            int id = MovieDAO.submitMovie(connection, expected);
            Movie actual = MovieDAO.getMovie(connection, id);
            assertEquals(expected, actual);
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
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void testGetMovies1() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> expected = new LinkedList<>();
            expected.add(new Movie(1, "test1", 2016, "genre_test"));
            expected.add(new Movie(2, "test2", 2016, "genre_test"));
            expected.add(new Movie(3, "test3", 2016, "genre_test"));
            expected.add(new Movie(4, "test4", 2016, "genre_test"));
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }

            List<Movie> actual = MovieDAO.getMovies(connection, 2, 0);
            assertEquals(2, actual.size());
            for (int i = 0; i < 2; i++) {
                assertEquals(actual.get(i), expected.get(i));
            }

            actual = MovieDAO.getMovies(connection, 2, 2);
            assertEquals(2, actual.size());
            for (int i = 0; i < 2; i++) {
                assertEquals(actual.get(i), expected.get(2+i));
            }
        }
    }

    @Test
    public void testGetMoviesWithSortByAddedDate() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> expected = new LinkedList<>();
            expected.add(new Movie(1, "test1", 2016, "genre_test"));
            expected.add(new Movie(2, "test2", 2016, "genre_test"));
            expected.add(new Movie(3, "test3", 2016, "genre_test"));
            expected.add(new Movie(4, "test4", 2016, "genre_test"));
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }

            List<Movie> actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.ADDED_DATE);
            assertEquals(4, actual.size());
            for (int i = 0; i < 4; i++) {
                assertEquals(actual.get(i), expected.get(i));
            }

            actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.ADDED_DATE_DESC);
            assertEquals(4, actual.size());
            for (int i = 0; i < 4; i++) {
                assertEquals(actual.get(i), expected.get(3-i));
            }
        }
    }

    @Test
    public void testGetMoviesWithSortByYear() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> expected = new LinkedList<>();
            expected.add(new Movie(1, "test1", 2016, "genre_test"));
            expected.add(new Movie(2, "test2", 2008, "genre_test"));
            expected.add(new Movie(3, "test3", 2013, "genre_test"));
            expected.add(new Movie(4, "test4", 2015, "genre_test"));
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }

            List<Movie> actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.YEAR);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(1));
            assertEquals(actual.get(1), expected.get(2));
            assertEquals(actual.get(2), expected.get(3));
            assertEquals(actual.get(3), expected.get(0));


            actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.YEAR_DESC);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(0));
            assertEquals(actual.get(1), expected.get(3));
            assertEquals(actual.get(2), expected.get(2));
            assertEquals(actual.get(3), expected.get(1));
        }
    }

    @Test
    public void testGetMoviesWithSortByTitle() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> expected = new LinkedList<>();
            expected.add(new Movie(1, "z", 2016, "genre_test"));
            expected.add(new Movie(2, "a", 2016, "genre_test"));
            expected.add(new Movie(3, "ab", 2016, "genre_test"));
            expected.add(new Movie(4, "bcd", 2016, "genre_test"));
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }

            List<Movie> actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.TITLE);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(1));
            assertEquals(actual.get(1), expected.get(2));
            assertEquals(actual.get(2), expected.get(3));
            assertEquals(actual.get(3), expected.get(0));


            actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.TITLE_DESC);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(0));
            assertEquals(actual.get(1), expected.get(3));
            assertEquals(actual.get(2), expected.get(2));
            assertEquals(actual.get(3), expected.get(1));
        }
    }

    @Test
    public void testGetMoviesWithSortByRating() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            List<Movie> expected = new LinkedList<>();
            expected.add(new Movie(1, "test1", 2016, "genre_test"));
            expected.add(new Movie(2, "test2", 2016, "genre_test"));
            expected.add(new Movie(3, "test3", 2016, "genre_test"));
            expected.add(new Movie(4, "test4", 2016, "genre_test"));
            for (Movie movie : expected) {
                MovieDAO.submitMovie(connection, movie);
            }
            //RatingDAO.submitRating(connection, new Rating(1, 9));
            RatingDAO.submitRating(connection, new Rating(1, 3));
            RatingDAO.submitRating(connection, new Rating(3, 1));
            RatingDAO.submitRating(connection, new Rating(4, 2));

            List<Movie> actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.RATING);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(1));
            assertEquals(actual.get(1), expected.get(2));
            assertEquals(actual.get(2), expected.get(3));
            assertEquals(actual.get(3), expected.get(0));


            actual = MovieDAO.getMovies(connection, -1, 0, Movie.Sort.RATING_DESC);
            assertEquals(4, actual.size());
            assertEquals(actual.get(0), expected.get(0));
            assertEquals(actual.get(1), expected.get(3));
            assertEquals(actual.get(2), expected.get(2));
            assertEquals(actual.get(3), expected.get(1));
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
            assertEquals(expected, actual);
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
            assertEquals(expected, actual);
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
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetLowestRatingMovie() throws Exception {
        Movie expected = new Movie(2, "test2", 2016, "genre1");
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            MovieDAO.submitMovie(connection, new Movie(1, "test1", 2016, "genre1"));
            MovieDAO.submitMovie(connection, expected);
            RatingDAO.submitRating(connection, new Rating(1, 4, 1));

            Movie actual = MovieDAO.getLowestRatingMovie(connection);
            assertEquals(expected, actual);
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
            assertEquals(expected, actual);
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


            assertEquals(2, actual.size());
            assertEquals(expected.get(1), actual.get(0));
            assertEquals(expected.get(0), actual.get(1));
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


            assertEquals(3, actual.size());
            assertEquals(expected.get(1), actual.get(0));
            assertEquals(expected.get(0), actual.get(1));
            assertEquals(expected.get(2), actual.get(2));
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


            assertEquals(2, actual.size());
            assertEquals(expected.get(2), actual.get(0));
            assertEquals(expected.get(0), actual.get(1));
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


            assertEquals(3, actual.size());
            assertEquals(expected.get(2), actual.get(0));
            assertEquals(expected.get(0), actual.get(1));
            assertEquals(expected.get(1), actual.get(2));
        }
    }

    @Test
    public void testGetMostReviewedMovie() throws Exception {
        Movie expected = new Movie(1, "test1", 2016, "genre_test");
        List<Movie> movies = new LinkedList<>();
        movies.add(expected);
        movies.add(new Movie(2, "test2", 2016, "genre_test"));
        movies.add(new Movie(3, "test3", 2016, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : movies) {
                MovieDAO.submitMovie(connection, movie);
            }
            ReviewDAO.submitReview(connection, new Review(1, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(1, 2, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(1, 3, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(1, 4, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 2, "Nuno", "Kickass Movie", null, 5));

            Movie actual = MovieDAO.getMostReviewedMovie(connection);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetMostReviewedMovieWithMoreThanOne() throws Exception {
        Movie expected = new Movie("test1", 2016, "genre_test");
        List<Movie> movies = new LinkedList<>();
        movies.add(new Movie("test2", 2016, "genre_test"));
        movies.add(expected);
        movies.add(new Movie("test3", 2015, "genre_test"));
        movies.add(new Movie("test4", 2015, "genre_test"));

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : movies) {
                MovieDAO.submitMovie(connection, movie);
            }
            ReviewDAO.submitReview(connection, new Review(1, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(1, 2, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 2, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(3, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(3, 2, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(4, 1, "Nuno", "Kickass Movie", null, 5));

            Movie actual = MovieDAO.getMostReviewedMovie(connection);

            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getReleaseYear(), actual.getReleaseYear());
        }
    }

    @Test
    public void testGetMostReviewedMovies() throws Exception {
        Movie expected1 = new Movie(2, "test1", 2016, "genre_test");
        Movie expected2 = new Movie(3, "test3", 2016, "genre_test");
        List<Movie> movies = new LinkedList<>();
        movies.add(new Movie(1, "test2", 2015, "genre_test"));
        movies.add(expected1);
        movies.add(expected2);
        movies.add(new Movie(4, "test4", 2015, "genre_test"));

        List<Movie> expected = new LinkedList<>();
        expected.add(expected1);
        expected.add(expected2);

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            for (Movie movie : movies) {
                MovieDAO.submitMovie(connection, movie);
            }
            ReviewDAO.submitReview(connection, new Review(1, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(4, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(2, 2, "Nuno", "Kickass Movie", null, 5));

            List<Movie> actual = MovieDAO.getMostReviewedMovies(connection, 2);

            assertEquals(2, actual.size());
            assertEquals(expected1, actual.get(0));

            ReviewDAO.submitReview(connection, new Review(1, 2, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(3, 1, "Nuno", "Kickass Movie", null, 5));
            ReviewDAO.submitReview(connection, new Review(3, 2, "Nuno", "Kickass Movie", null, 5));

            actual = MovieDAO.getMostReviewedMovies(connection, 2);

            assertEquals(2, actual.size());
            assertTrue(actual.containsAll(expected));

        }
    }

    @Test(expected = NoDataException.class)
    public void testGetMostReviewedMoviesWithoutMovies() throws Exception {

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            MovieDAO.getMostReviewedMovies(connection, 90);
        }
    }
}