package pt.isel.ls.movies.engine;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Class to test {@link Request}
 */
public class RequestTest {

    @Test
    public void getQueryParams() throws Exception {
        Map<String, String> expected= new HashMap<>();
        expected.put("name", "Avengers");
        expected.put("rating", "5");

        String[] args = new String[]{"POST", "/movies/123", "name=Avengers&rating=5"};
        Request request = Request.create(args);

        assertEquals(expected.get("name"), request.getParameter("name"));
        assertEquals(expected.get("rating"), request.getParameter("rating"));
    }

    @Test
    public void getQueryHeaders() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("accept", "text/plain");
        expected.put("accept-language", "en-gb");

        String[] args = new String[]{"GET", "/movies/123", "accept:text/plain|accept-language:en-gb"};
        Request request = Request.create(args);

        assertEquals(expected.get("accept"), request.getHeader("accept"));
        assertEquals(expected.get("accept-language"), request.getHeader("accept-language"));
    }

    @Test
    public void getQueryHeadersAndParameters() throws Exception {
        String[] args = new String[]{
                "POST",
                "/movies/123",
                "accept:text/plain|accept-language:en-gb",
                "name=Avengers&rating=5"};
        Request request = Request.create(args);

        assertEquals("text/plain", request.getHeader("accept"));
        assertEquals("en-gb", request.getHeader("accept-language"));

        assertEquals("Avengers", request.getParameter("name"));
        assertEquals("5", request.getParameter("rating"));
    }

    @Test
    public void create() throws Exception {
        String[] args = new String[]{"GET", "/movies"};
        Request.create(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badCreate() throws Exception {
        String[] args = new String[]{"GET"};
        Request.create(args);
    }
}