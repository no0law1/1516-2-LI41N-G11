package pt.isel.ls.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.ContextData;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.engine.Router;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class whose instance represents the point of entry of the application.
 */
public class MoviesApp {

    private static Logger logger = LoggerFactory.getLogger(MoviesApp.class);

    private final Router router;
    private ContextData contextData;

    public MoviesApp() throws Exception {
        router = Router.createRouter(DataSourceFactory.createInstance());
        contextData = new ContextData();
        contextData.router = router;
    }

    private void run(String[] args) throws Exception {
        contextData.dynamic = false;
        try {
            Request request = Request.create(contextData, args);
            Response response = Response.create(request.getHeader("file-name"));
            router.get(request).execute(request, response);
        } catch (Exception e) {
            logger.error("run from String[]: ", e);
        }
    }

    private void run() {
        contextData.dynamic = true;
        while (true) {
            try {
                System.out.print("Enter Request: ");
                String route = new Scanner(System.in).nextLine();
                Request request = Request.create(contextData, route.split(" "));
                Response response = Response.create(request.getHeader("file-name"));
                router.get(request).execute(request, response);
            } catch (Exception e) {
                logger.error("run from dynamic: ", e);
            }
        }
    }

    public static void main(String[] args){
        try {
            MoviesApp app = new MoviesApp();
            if (args.length >= 1) {
                logger.info("running command with args: " +  Arrays.toString(args));
                app.run(args);
            } else {
                logger.info("running dynamically");
                app.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
