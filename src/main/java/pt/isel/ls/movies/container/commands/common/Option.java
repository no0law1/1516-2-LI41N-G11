package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.utils.FileUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Lists all the commands available and their description
 */
public class Option extends Command {

    private static final String DETAILS = "Lists all the available options";

    private static final String METHOD = "OPTION";

    private static final String PATH = "/";

    public Option(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", ";");

        for (String s : map.keySet()) {
            System.out.printf("%s\n\t%s\n", s, map.get(s));
        }
    }

}
