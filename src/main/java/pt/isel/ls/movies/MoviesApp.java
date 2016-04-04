package pt.isel.ls.movies;

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

    public static void main(String[] args){
        try {
            new MoviesApp().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(String[] args) throws Exception {
        Request request = Request.create(args);
        router.get(request).execute(request);
    }
}
