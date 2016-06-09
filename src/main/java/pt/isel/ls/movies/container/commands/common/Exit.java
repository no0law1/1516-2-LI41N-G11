package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.container.commands.collections.RemoveMovieFromCollection;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

/**
 * Used to exit the application
 */
public class Exit extends Command {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new Exit(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("EXIT", "/", null, "Exits the application");
        }
    };

    public Exit(DataSource dataSource) {
        super(dataSource);
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
