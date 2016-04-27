package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.utils.FileUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Lists all the commands available and their description
 */
public class Option implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", FileUtils.Option.OPTIONS);

        for (String s : map.keySet()) {
            System.out.printf("%s\n\t%s\n", s, map.get(s));
        }
    }

}