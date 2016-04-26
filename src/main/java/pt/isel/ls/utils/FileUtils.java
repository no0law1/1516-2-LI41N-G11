package pt.isel.ls.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles common file operations
 */
public class FileUtils {

    /**
     * Retrieves from a file, a set key value pairs to a map
     *
     * @param filePath  the path to the file
     * @param separator the separator of key and value
     * @return mapped key value from file
     * @throws FileNotFoundException if file does not exist
     */
    public static Map<String, String> getFromFile(String filePath, String separator) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();

        Scanner scanner = new Scanner(new FileReader(new File(filePath)));

        while (scanner.hasNext()) {
            String[] args = scanner.nextLine().split(separator);
            map.put(args[0], args[1]);
        }

        return map;
    }

}
