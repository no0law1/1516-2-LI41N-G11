package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

/**
 * Used to exit the application
 */
public class Exit extends Command {

    public Exit(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        System.out.println("Closing your application...");
        System.exit(0);
    }

}
