package pt.isel.ls.movies.engine;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.container.commands.Movies.*;
import pt.isel.ls.movies.container.commands.Rating.GetMovieRating;
import pt.isel.ls.movies.container.commands.Rating.PostRating;
import pt.isel.ls.movies.container.commands.Review.GetMovieReviews;
import pt.isel.ls.movies.container.commands.Review.GetReview;
import pt.isel.ls.movies.container.commands.Review.PostReview;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class whose instance contains the routing for a set of Commands
 */
public class Router {
    private Node methods;

    public Router(){
        methods = new Node();
    }

    public void add(String method, String path, ICommand ICommand){
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

    public ICommand get(Request request) throws UnsupportedEncodingException {
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
