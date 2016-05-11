package pt.isel.ls.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests methods involving files
 */
public class FileUtilsTest {

    @Test
    public void testGetFromFile() throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", FileUtils.Option.COMMANDS);

        assertEquals(map.size(), 22);
        assertNotNull(map);
    }

    @Test
    public void testGetFromFileOptions() throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", FileUtils.Option.OPTIONS);

        assertEquals(map.size(), 22);
        assertNotNull(map);
    }
}