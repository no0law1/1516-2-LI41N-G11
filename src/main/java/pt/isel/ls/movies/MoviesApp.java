package pt.isel.ls.movies;

import pt.isel.ls.movies.container.commands.collections.*;
import pt.isel.ls.movies.container.commands.common.Exit;
import pt.isel.ls.movies.container.commands.common.Option;
import pt.isel.ls.movies.container.commands.movies.*;
import pt.isel.ls.movies.container.commands.rating.GetMovieRating;
import pt.isel.ls.movies.container.commands.rating.PostRating;
import pt.isel.ls.movies.container.commands.review.GetMovieReviews;
import pt.isel.ls.movies.container.commands.review.GetReview;
import pt.isel.ls.movies.container.commands.review.PostReview;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Router;

import java.util.Scanner;

/**
 * Class whose instance represents the point of entry of the application.
 */
public class MoviesApp {

    private final Router router;

    public MoviesApp(){
        router = new Router();
        createRouter();
    }

    //TODO: this method should disappear in order to create a new one on Router to load the Router info from a file.
    public final void createRouter(){
        /**  Where Commands should be added  **/

        /**  MOVIES  **/
        router.add("POST", "/movies", new PostMovie());
        router.add("GET", "/movies", new GetMovieList());
        router.add("GET", "/movies/{mid}", new GetMovie());
        router.add("GET", "/tops/ratings/higher/average", new GetHighestRatedMovie());
        router.add("GET", "/tops/{n}/ratings/higher/average", new GetHighestRatedMovies());
        router.add("GET", "/tops/ratings/lower/average", new GetLowestRatedMovie());
        router.add("GET", "/tops/{n}/ratings/lower/average", new GetLowestRatedMovies());
        router.add("GET", "/tops/reviews/higher/count", new GetMostReviewedMovie());
        router.add("GET", "/tops/{n}/reviews/higher/count", new GetMostReviewedMovies());

        /**  RATING  **/
        router.add("POST", "/movies/{mid}/ratings", new PostRating());
        router.add("GET", "/movies/{mid}/ratings", new GetMovieRating());

        /**  REVIEW  **/
        router.add("POST", "/movies/{mid}/reviews", new PostReview());
        router.add("GET", "/movies/{mid}/reviews", new GetMovieReviews());
        router.add("GET", "/movies/{mid}/reviews/{rid}", new GetReview());

        /**  COLLECTION  **/
        router.add("POST", "/collections", new CreateCollection());
        router.add("POST", "/collections/{cid}/movies", new PostMovieToCollection());
        router.add("GET", "/collections", new GetCollections());
        router.add("GET", "/collections/{cid}", new GetCollection());
        router.add("DELETE", "/collections/{cid}/movies/{mid}", new RemoveMovieFromCollection());

        /**  COMMON  **/
        router.add("EXIT", "/", new Exit());
        router.add("OPTION", "/", new Option());
    }

    private void run(String[] args) throws Exception {
        try {
            Request request = Request.create(args);
            router.get(request).execute(DataSourceFactory.createInstance(), request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void run() throws Exception {
        try {
            //TODO: Must exit the application only on exit
            while (true) {
                System.out.print("Enter Request: ");
                String route = new Scanner(System.in).nextLine();
                Request request = Request.create(route.split(" "));
                router.get(request).execute(DataSourceFactory.createInstance(), request);
                //TODO: Handle exceptions here
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        try {
            MoviesApp app = new MoviesApp();
            if (args.length >= 2) {
                app.run(args);
            } else {
                app.run();
                //System.out.println("usage: ./prog {method} {path} {parameters}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
