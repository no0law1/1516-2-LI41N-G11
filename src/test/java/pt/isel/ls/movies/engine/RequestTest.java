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

        Map<String, String> actual = request.getQueryParams();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get("name"), actual.get("name"));
        assertEquals(expected.get("rating"), actual.get("rating"));
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