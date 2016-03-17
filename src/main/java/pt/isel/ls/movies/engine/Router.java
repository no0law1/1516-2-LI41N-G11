package pt.isel.ls.movies.engine;

import pt.isel.ls.movies.engine.commands.Command;
import pt.isel.ls.movies.engine.commands.Movies.GetMovie;
import pt.isel.ls.movies.engine.commands.Movies.GetMovieList;
import pt.isel.ls.movies.engine.commands.Movies.PostMovie;
import pt.isel.ls.movies.engine.commands.Review.GetReview;
import pt.isel.ls.movies.engine.commands.Tops.GetNTopsRatingHigherAverage;
import pt.isel.ls.movies.engine.commands.Tops.GetTopsRatingHigherAverage;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: comment
 */
public class Router {
    private class Node {
        private Command command;
        private Map<String, Node> nodes;

        public Node(){
            this(null);
        };

        public Node(Command command){
            this.command = command;
            nodes = new HashMap<>();
        }

        public Node get(String key){
            return nodes.get(key);
        }

        public Command getCommand(){
            return command;
        };


        public void setCommand(Command command){
            this.command = command;
        }
    }

    private Node methods;

    private Router(){
        methods = new Node();
    }

    private void add(String method, String path, Command command){
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
        node.setCommand(command);
    }

    public Command get(String method, String path){
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

    public static Router createRouter(){
        Router router = new Router();
        router.add("GET", "/movies", new GetMovieList());
        router.add("GET", "/movies/{p}", new GetMovie());
        router.add("GET", "/movies/{p}/reviews/{p}", new GetReview());
        router.add("GET", "/tops/{p}/ratings/higher/average", new GetNTopsRatingHigherAverage());
        router.add("POST", "/movies/", new PostMovie());
        router.add("GET", "/tops/ratings/higher/average", new GetTopsRatingHigherAverage());
        return router;
    }
}
