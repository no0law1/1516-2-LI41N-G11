package pt.isel.ls.movies;

import pt.isel.ls.movies.commands.Command;
import pt.isel.ls.movies.commands.GetMovies;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: comment
 */
public class Router {
    public class RouterNode{
        private Command command;
        private Map<String, RouterNode> nodes;

        public RouterNode(){
            this(null);
        };

        public RouterNode(Command command){
            this.command = command;
            nodes = new HashMap<>();
        }

        public RouterNode get(String key){
            return nodes.get(key);
        }

        public Command getCommand(){
            return command;
        };


        public void setCommand(Command command){
            this.command = command;
        }
    }

    private RouterNode methods;

    private Router(){
        methods = new RouterNode();
    }

    private void add(String method, String path, Command command){
        RouterNode node = methods.get(method);
        if(node == null){
            node = new RouterNode();
            methods.nodes.put(method, node);
        }
        for (String name: path.split("/")) {
            node = node.get(name);
            if(node == null){
                node = new RouterNode();
                methods.nodes.put(method, node);
            }
        }
        node.setCommand(command);
    }

    public Command get(String method, String path){
        RouterNode node = methods.get(method);
        if(node == null){
            throw new UnsupportedOperationException("Method not found");
        }
        for (String name: path.split("/")) {
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
        router.add("GET", "/movies", new GetMovies());
        router.add("GET", "/movies/{p}", new GetMovies());
    }
}
