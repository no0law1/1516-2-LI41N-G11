package pt.isel.ls.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Tests methods involving files
 */
public class FileUtilsTest {

    @Test
    public void testGetFromFile() throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", ";");

        assertNotNull(map);
    }

    @Test
    public void testGetFromFileOptions() throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", ";");

        assertNotNull(map);
    }
}