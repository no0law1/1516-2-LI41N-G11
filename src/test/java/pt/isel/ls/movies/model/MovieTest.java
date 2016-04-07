package pt.isel.ls.movies.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing class of model class {@link Movie}
 */
public class MovieTest {

    @Test
    public void testEqualsTrue() throws Exception {
        Movie expected = new Movie(1, "title", 1993, "acao");
        Movie actual = new Movie(1, "title", 1993, "acao");

        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Movie movie = new Movie(1, "title", 1993, "acao");
        Movie movie3 = new Movie(2, "title", 1993, "acao");

        assertFalse(movie.equals(movie3));
    }

    @Test
    public void testEqualsFalse2() throws Exception {
        Movie movie = new Movie(1, "title", 1993, "acao");
        Movie movie3 = new Movie(1, "title2", 1993, "acao");

        assertFalse(movie.equals(movie3));
    }
}