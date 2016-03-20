package pt.isel.ls.movies.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testing class of model class Movie
 */
public class MovieTest {

    @Test
    public void testEqualsTrue() throws Exception {
        Movie movie = new Movie(1, "title", 1993, "acao");
        Movie movie2 = new Movie(1, "title", 1993, "acao");

        assertTrue(movie.equals(movie2));
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