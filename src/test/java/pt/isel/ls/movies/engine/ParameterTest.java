package pt.isel.ls.movies.engine;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Parameter class
 */
public class ParameterTest {

    @Test
    public void testCreate() throws Exception {
        Parameter actual = Parameter.create(null, "name=LS&description=software+laboratory");
        Map<String, String> expected = new HashMap<>();
        expected.put("name", "LS");
        expected.put("description", "software+laboratory");

        assertEquals(actual.getValue("name"), expected.get("name"));
        assertEquals(actual.getValue("description"), expected.get("description"));
    }

}