package pt.isel.ls.movies.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testing class of model class Rating
 */
public class RatingTest {

    @Test
    public void testEqualsTrue() throws Exception {
        Rating expected = new Rating(1, 1, 1);
        Rating actual = new Rating(1, 1, 1);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Rating expected = new Rating(1, 1, 1);
        Rating actual = new Rating(2, 1, 1);

        assertFalse(expected.equals(actual));
    }
}