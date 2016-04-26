package pt.isel.ls.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * TODO: Commentary.
 */
public class FileUtilsTest {

    @Test
    public void getFromFile() throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", ";");

        assertNotNull(map);
    }

    //TODO: better tests
}