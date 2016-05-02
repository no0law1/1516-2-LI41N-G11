package pt.isel.ls.movies;

import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Router;

import java.util.Scanner;

/**
 * Class whose instance represents the point of entry of the application.
 */
public class MoviesApp {

    private final Router router;

    public MoviesApp() throws Exception {
        router = Router.createRouter();
    }

    private void run(String[] args) throws Exception {
        try {
            Request request = Request.create(args);
            router.get(request).execute(DataSourceFactory.createInstance(), request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void run() {
        while (true) {
            try {
                System.out.print("Enter Request: ");
                String route = new Scanner(System.in).nextLine();
                Request request = Request.create(route.split(" "));
                router.get(request).execute(DataSourceFactory.createInstance(), request);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args){
        try {
            MoviesApp app = new MoviesApp();
            if (args.length >= 1) {
                app.run(args);
            } else {
                app.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
