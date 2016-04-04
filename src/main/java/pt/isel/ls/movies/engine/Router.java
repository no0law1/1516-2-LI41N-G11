package pt.isel.ls.movies.engine;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.container.commands.Movies.*;
import pt.isel.ls.movies.container.commands.Rating.GetMovieRating;
import pt.isel.ls.movies.container.commands.Rating.PostRating;
import pt.isel.ls.movies.container.commands.Review.GetMovieReviews;
import pt.isel.ls.movies.container.commands.Review.GetReview;
import pt.isel.ls.movies.container.commands.Review.PostReview;
import pt.isel.ls.movies.container.commands.Tops.GetNTopsRatingHigherAverage;
import pt.isel.ls.movies.container.commands.Tops.GetNTopsRatingLowerAverage;
import pt.isel.ls.movies.container.commands.Tops.GetTopsRatingHigherAverage;
import pt.isel.ls.movies.container.commands.Tops.GetTopsRatingLowerAverage;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: comment
 */
public class Router {
    private Node methods;

    private Router(){
        methods = new Node();
    }

    public static Router createRouter(){
        /**  Where Commands should be added  **/
        Router router = new Router();

        /**  MOVIES  **/
        router.add("POST", "/movies", new PostMovie("/movies"));
        router.add("GET", "/movies", new GetMovieList("/movies"));
        router.add("GET", "/movies/{p}", new GetMovie("/movies/{mid}"));
        router.add("GET", "/tops/reviews/higher/count", new GetHighestRatedMovie("/tops/reviews/higher/count"));
        router.add("GET", "/tops/{n}/reviews/higher/count", new GetHighestRatedMovies("/tops/{n}/reviews/higher/count"));

        /**  RATING  **/
        router.add("POST", "/movies/{p}/ratings", new PostRating("/movies/{mid}/ratings"));
        router.add("GET", "/movies/{p}/ratings", new GetMovieRating("/movies/{mid}/ratings"));

        /**  REVIEW  **/
        router.add("POST", "/movies/{p}/reviews", new PostReview("/movies/{mid}/reviews"));
        router.add("GET", "/movies/{p}/reviews", new GetMovieReviews("/movies/{mid}/reviews"));
        router.add("GET", "/movies/{p}/reviews/{p}", new GetReview("/movies/{mid}/reviews/{rid}"));

        /**  TOPS  **/
        router.add("GET", "/tops/ratings/higher/average", new GetTopsRatingHigherAverage("/tops/ratings/higher/average"));
        router.add("GET", "/tops/{p}/ratings/higher/average", new GetNTopsRatingHigherAverage("/tops/{n}/ratings/higher/average"));
        router.add("GET", "/tops/ratings/lower/average", new GetTopsRatingLowerAverage("/tops/ratings/lower/average"));
        router.add("GET", "/tops/{p}/ratings/lower/average", new GetNTopsRatingLowerAverage("/tops/{n}/ratings/lower/average"));

        return router;
    }

    private void add(String method, String path, ICommand ICommand){
        Node node = methods.get(method);
        if(node == null){
            node = new Node();
            methods.nodes.put(method, node);
        }
        for (String name: path.split("/")) {
            if(name.isEmpty()) continue;
            Node auxNode = node.get(name);
            if(auxNode == null){
                auxNode = new Node();
                node.nodes.put(name, auxNode);
            }
            node = auxNode;
        }
        node.setCommand(ICommand);
    }

    public ICommand get(Request request){
        return get(request.getMethod(), request.getPath());
    }

    public ICommand get(String method, String path){
        Node node = methods.get(method);
        if(node == null){
            throw new UnsupportedOperationException("Method not found");
        }
        for (String name: path.split("/")) {
            if(name.isEmpty()) continue;
            if(Character.isDigit(name.charAt(0))){
                name = "{p}";
            }
            node = node.get(name);
            if(node == null){
                throw new UnsupportedOperationException("Path not found");
            }
        }
        return node.getCommand();
    }

    private class Node {
        private ICommand ICommand;
        private Map<String, Node> nodes;

        public Node(){
            this(null);
        };

        public Node(ICommand ICommand){
            this.ICommand = ICommand;
            nodes = new HashMap<>();
        }

        public Node get(String key){
            return nodes.get(key);
        }

        public ICommand getCommand(){
            return ICommand;
        };


        public void setCommand(ICommand ICommand){
            this.ICommand = ICommand;
        }
    }
}
