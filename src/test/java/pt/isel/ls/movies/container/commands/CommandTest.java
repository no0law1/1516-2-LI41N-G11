package pt.isel.ls.movies.container.commands;

import org.junit.Test;
import pt.isel.ls.movies.engine.Request;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Commentary.
 */
public class CommandTest {

    @Test
    public void getBinds() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("mid", "123");

        Command command = new Command("/movies/{mid}") {
            @Override
            public void execute(Request request) throws Exception {
                throw new UnsupportedOperationException();
            }
        };

        Map<String, String> actual = command.getBinds("/movies/123");

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get("mid"), actual.get("mid"));
    }

    @Test
    public void getTwoBinds() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("mid", "123");
        expected.put("rid", "456");

        Command command = new Command("/movies/{mid}/review/{rid}") {
            @Override
            public void execute(Request request) throws Exception {
                throw new UnsupportedOperationException();
            }
        };

        Map<String, String> actual = command.getBinds("/movies/123/review/456");

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get("mid"), actual.get("mid"));
        assertEquals(expected.get("rid"), actual.get("rid"));
    }
}