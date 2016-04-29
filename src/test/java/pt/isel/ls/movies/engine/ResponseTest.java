package pt.isel.ls.movies.engine;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Test class {@link Response}
 */
public class ResponseTest {
    @Test
    public void write() throws Exception {
        File file = new File("src/test/java/test.txt");
        Response response = Response.create(file);

        response.write("This a txt file");

        assertTrue(file.exists());
        assertTrue(file.delete());
    }

}