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
        router.add("POST", "/movies", new PostMovie());
        router.add("GET", "/movies", new GetMovieList());
        router.add("GET", "/movies/{mid}", new GetMovie());
        router.add("GET", "/tops/reviews/higher/count", new GetHighestRatedMovie());
        router.add("GET", "/tops/{n}/reviews/higher/count", new GetHighestRatedMovies());

        /**  RATING  **/
        router.add("POST", "/movies/{mid}/ratings", new PostRating());
        router.add("GET", "/movies/{mid}/ratings", new GetMovieRating());

        /**  REVIEW  **/
        router.add("POST", "/movies/{mid}/reviews", new PostReview());
        router.add("GET", "/movies/{mid}/reviews", new GetMovieReviews());
        router.add("GET", "/movies/{mid}/reviews/{rid}", new GetReview());

        /**  TOPS  **/
        router.add("GET", "/tops/ratings/higher/average", new GetTopsRatingHigherAverage());
        router.add("GET", "/tops/{n}/ratings/higher/average", new GetNTopsRatingHigherAverage());
        router.add("GET", "/tops/ratings/lower/average", new GetTopsRatingLowerAverage());
        router.add("GET", "/tops/{n}/ratings/lower/average", new GetNTopsRatingLowerAverage());

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
            if(name.startsWith("{")) {
                if(!name.endsWith("}")) {
                    throw new IllegalArgumentException("A path parameter must ends with \"}\"");
                }
                node = addParameter(node, name);
            }
            else {
                node = addNode(node, name);
            }
        }
        node.setCommand(ICommand);
    }

    public Node addNode(Node node, String name){
        Node auxNode = node.get(name);
        if(auxNode == null){
            auxNode = new Node();
            node.nodes.put(name, auxNode);
        }
        return auxNode;
    }

    public Node addParameter(Node node, String name){
        Node auxNode;
        if(node.parameter == null){
            auxNode = new Node();
            node.parameter = new Parameter(name, auxNode);
        }
        else{
            auxNode = node.parameter.node;
        }
        return auxNode;
    }

    public ICommand get(Request request){
        Node node = methods.get(request.getMethod());
        if(node == null){
            throw new UnsupportedOperationException("Method not found");
        }
        for (String name: request.getPath().split("/")) {
            if(name.isEmpty()) continue;
            Node auxNode = node.get(name);
            if(auxNode == null){
                if(node.parameter == null) {
                    throw new UnsupportedOperationException("Path not found");
                }
                request.getQueryParams().put(node.parameter.getParameterName(), name);
                node = node.parameter.node;
            }
            else {
                node = auxNode;
            }
        }
        return node.getCommand();
    }

    private class Parameter{
        private final String name;
        private final Node node;

        private Parameter(String name, Node node) {
            this.name = name;
            this.node = node;
        }

        private String getParameterName(){
            return name.substring(1, name.length()-1);
        }
    }

    private class Node {
        private ICommand iCommand;
        private Map<String, Node> nodes;
        private Router.Parameter parameter;

        public Node(){
            this(null);
        }

        public Node(ICommand iCommand){
            this.iCommand = iCommand;
            nodes = new HashMap<>();
        }

        public Node get(String key){
            return nodes.get(key);
        }

        public ICommand getCommand(){
            return iCommand;
        }


        public void setCommand(ICommand iCommand){
            this.iCommand = iCommand;
        }
    }
}
