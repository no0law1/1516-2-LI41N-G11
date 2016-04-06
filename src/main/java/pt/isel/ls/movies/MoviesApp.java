package pt.isel.ls.movies;

import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Router;

/**
 * TODO: comment
 */
public class MoviesApp {

    private final Router router;

    public MoviesApp(){
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

    public static void main(String[] args){
        try {
            new MoviesApp().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
