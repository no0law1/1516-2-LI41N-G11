package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Used to exit the application
 */
public class Exit extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        System.out.println("Closing your application...");
        System.exit(0);
    }

}
