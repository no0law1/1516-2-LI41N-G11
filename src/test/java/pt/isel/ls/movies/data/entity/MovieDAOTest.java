package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

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
        //TODO: Test
    }

    @Test
    public void testGetLowestRatingMovie() throws Exception {

    }

    @Test
    public void testGetHighestRatingMovies() throws Exception {

    }

    @Test
    public void testGetLowestRatingMovies() throws Exception {

    }
}