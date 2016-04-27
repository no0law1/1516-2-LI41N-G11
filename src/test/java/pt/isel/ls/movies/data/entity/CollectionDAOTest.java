package pt.isel.ls.movies.data.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class of model class {@link CollectionDAO}
 */
public class CollectionDAOTest {

    public static DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = DataSourceFactory.createTestInstance();
    }

    @After
    public void tearDown() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("ALTER SEQUENCE collection_id_seq RESTART WITH 1").execute();
            connection.prepareStatement("ALTER SEQUENCE movie_id_seq RESTART WITH 1").execute();
        }
    }

    @Test
    public void createCollection() throws Exception {
        int expected = 1;
        int actual;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            actual = CollectionDAO.createCollection(connection, new Collection("Collection1", "description"));
        }
        assertEquals(expected, actual);
    }

    @Test(expected = SQLException.class)
    public void createCollectionException() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            CollectionDAO.createCollection(connection, new Collection("Collection1", "description"));
            Collection expected = new Collection(2, "Collection1", "descriptionz");
            CollectionDAO.createCollection(connection, expected);
        }
    }

    @Test
    public void getCollection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            CollectionDAO.createCollection(connection, new Collection("Collection1", "description"));
            Collection expected = new Collection(2, "Collection2", "descriptionz");
            int id = CollectionDAO.createCollection(connection, expected);
            Collection actual = CollectionDAO.getCollection(connection, id);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void getCollections() throws Exception {
        List<Collection> expected;
        List<Collection> actual = new LinkedList<>();
        actual.add(new Collection(1, "name1", "description1"));
        actual.add(new Collection(2, "name2", "description2"));
        actual.add(new Collection(3, "name3", "description3"));
        actual.add(new Collection(4, "name4", "description4"));

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            for (Collection collection : actual) {
                CollectionDAO.createCollection(connection, collection);
            }
            expected = CollectionDAO.getCollections(connection);
        }

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testGetCollections1() throws Exception {
        List<Collection> expected = new LinkedList<>();
        expected.add(new Collection(1, "name1", "description1"));
        expected.add(new Collection(2, "name2", "description2"));
        expected.add(new Collection(3, "name3", "description3"));
        expected.add(new Collection(4, "name4", "description4"));

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            for (Collection collection : expected) {
                CollectionDAO.createCollection(connection, collection);
            }
            List<Collection> actual = CollectionDAO.getCollections(connection, 2, 0);
            assertEquals(2, actual.size());
            for (int i = 0; i < 2; i++) {
                assertEquals(actual.get(i), expected.get(i));
            }

            actual = CollectionDAO.getCollections(connection, 2, 2);
            assertEquals(2, actual.size());
            for (int i = 0; i < 2; i++) {
                assertEquals(actual.get(i), expected.get(2+i));
            }
        }


    }

    @Test
    public void postMovieToCollection() throws Exception {
        Collection.MovieCollectionUID actual;
        Movie movie = new Movie(2, "title", 2016, "genre");
        Collection.MovieCollectionUID expected = new Collection.MovieCollectionUID(1, 2);

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            int id = CollectionDAO.createCollection(connection, new Collection("Collection1", "description"));
            MovieDAO.submitMovie(connection, new Movie(1, "title2", 2016, "genre"));
            MovieDAO.submitMovie(connection, movie);

            actual = CollectionDAO.postMovieToCollection(connection, id, movie.getId());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeMovieFromCollection() throws Exception {
        Movie movie = new Movie(1, "title", 2016, "genre");
        Movie movie1 = new Movie(2, "title2", 2016, "genre");

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            int id = CollectionDAO.createCollection(connection, new Collection("Collection1", "description"));
            MovieDAO.submitMovie(connection, movie);
            MovieDAO.submitMovie(connection, movie1);

            CollectionDAO.postMovieToCollection(connection, id, movie.getId());
            CollectionDAO.postMovieToCollection(connection, id, movie1.getId());

            assertTrue(CollectionDAO.removeMovieFromCollection(connection, id, movie1.getId()));
        }
    }
}