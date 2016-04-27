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

    public enum Option {
        OPTIONS,
        COMMANDS
    }

    /**
     * Retrieves from a file, a set key value pairs to a map
     *
     * @param filePath  the path to the file
     * @return mapped key value from file
     * @throws FileNotFoundException if file does not exist
     */
    public static Map<String, String> getFromFile(String filePath, Option option) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();

        Scanner scanner = new Scanner(new FileReader(new File(filePath)));

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
                String[] args = line.split(";");
                if (option.equals(Option.COMMANDS)) {
                    map.put(args[0], args[1]);
                } else {
                    map.put(args[0].concat(" " + args[2]), args[3]);
                }
            }
        }

        return map;
    }

}
