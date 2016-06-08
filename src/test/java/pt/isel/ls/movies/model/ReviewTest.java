package pt.isel.ls.movies.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing class of model class {@link Review}
 */
public class ReviewTest {

    @Test
    public void testEquals() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "Kickass Movie", "review1", 5);
        Review actual = new Review(1, 1, "Nuno", "Kickass Movie", "review1", 5);

        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsWithNoReview() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "Kickass Movie", null, 5);
        Review actual = new Review(1, 1, "Nuno", "Kickass Movie", null, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsWithReviewAndWithout() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "Kickass Movie", "review1", 5);
        Review actual = new Review(1, 1, "Nuno", "Kickass Movie", null, 5);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Review expected = new Review(1, 1, "Nuno", "Kickass Movie", "review1", 5);
        Review actual = new Review(1, 1, "Nuo", "Kickass Movie", "review1", 5);

        assertFalse(expected.equals(actual));
    }
}