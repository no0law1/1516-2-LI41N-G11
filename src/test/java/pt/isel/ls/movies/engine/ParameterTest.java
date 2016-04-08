package pt.isel.ls.movies.engine;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link pt.isel.ls.movies.engine.Router.Parameter}
 */
public class ParameterTest {

    @Test
    public void testCreate() throws Exception {
        Parameter actual = Parameter.create(null, "name=LS&description=software+laboratory");

        assertEquals("LS", actual.getValue("name"));
        assertEquals("software+laboratory", actual.getValue("description"));
    }

}