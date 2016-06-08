package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

/**
 * Used to exit the application
 */
public class Exit extends Command {

    private static final String DETAILS = "Exits the application";

    private static final String METHOD = "EXIT";

    private static final String PATH = "/";

    public Exit(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }


    @Override
    public void execute(Request request, Response response) throws Exception {
        if(request.getContextData().runningServer != null){
            System.out.println("stopping web server...");

            request.getContextData().runningServer.stop();
            request.getContextData().runningServer.join();
        }

        System.out.println("Closing your application...");
        System.exit(0);
    }
}
