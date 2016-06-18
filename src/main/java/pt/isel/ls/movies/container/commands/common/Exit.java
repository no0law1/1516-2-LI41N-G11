package pt.isel.ls.movies.container.commands.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.container.commands.collections.RemoveMovieFromCollection;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

/**
 * Used to exit the application
 */
public class Exit extends Command {

    private static Logger logger = LoggerFactory.getLogger(Exit.class);

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
            logger.info("stopping web server...");

            request.getContextData().runningServer.stop();
            request.getContextData().runningServer.join();
        }

        logger.info("Closing application...");
        System.exit(0);
    }
}
