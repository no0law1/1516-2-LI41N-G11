package pt.isel.ls.movies.engine;

import org.reflections.Reflections;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.exceptions.HTMLException;
import pt.isel.ls.movies.exceptions.MethodNotAllowedException;
import pt.isel.ls.movies.exceptions.PathNotFoundException;
import pt.isel.ls.movies.view.errors.ErrorView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class whose instance contains the routing for a set of Commands
 */
public class Router {

    private class Parameter {
        private final String name;
        private final Node node;

        private Parameter(String name, Node node) {
            this.name = name;
            this.node = node;
        }

        private String getParameterName() {
            return name.substring(1, name.length() - 1);
        }
    }

    private class Node {
        private ICommand iCommand;
        private Map<String, Node> nodes;
        private Router.Parameter parameter;

        public Node() {
            this(null);
        }

        public Node(ICommand iCommand) {
            this.iCommand = iCommand;
            nodes = new HashMap<>();
        }

        public Node get(String key) {
            return nodes.get(key);
        }

        public ICommand getCommand() {
            return iCommand;
        }


        public void setCommand(ICommand iCommand) {
            this.iCommand = iCommand;
        }
    }

    private Node methods;

    public Router(){
        methods = new Node();
    }

    public void add(String method, String path, ICommand ICommand){
        method = method.toLowerCase();
        path = path.toLowerCase();
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
        name = name.toLowerCase();
        Node auxNode = node.get(name);
        if(auxNode == null){
            auxNode = new Node();
            node.nodes.put(name, auxNode);
        }
        return auxNode;
    }

    public Node addParameter(Node node, String name){
        name = name.toLowerCase();
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

    public ICommand get(Request request) throws MethodNotAllowedException, PathNotFoundException,
        UnsupportedEncodingException{
        Node node = methods.get(request.getMethod());
        if(node == null){
            throw new MethodNotAllowedException("Method not found");
        }
        for (String name: request.getPath().split("/")) {
            if(name.isEmpty()) continue;
            Node auxNode = node.get(name);
            if(auxNode == null){
                if(node.parameter == null) {
                    throw new PathNotFoundException("Path not found");
                }
                request.getParametersMap().put(node.parameter.getParameterName(), name);
                node = node.parameter.node;
            }
            else {
                node = auxNode;
            }
        }
        return node.getCommand();
    }

    public static Router createRouter(DataSource ds) throws Exception {
        Router router = new Router();
        populate(router, ds);
        return router;
    }

    private static void populate(Router router, DataSource ds) throws Exception {
        Reflections reflections = new Reflections("pt.isel.ls.movies.container.commands");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        classes.forEach(
                aClass -> {
                    try {
                        ICommand command = aClass.getDeclaredConstructor(DataSource.class).newInstance(ds);
                        router.add(command.getMethod(), command.getPath(), command);
                    } catch (NoSuchMethodException
                            | IllegalAccessException
                            | InvocationTargetException
                            | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public class RouterHttpServlet extends HttpServlet{
        protected Router router;
        private ContextData contextData;

        public RouterHttpServlet(ContextData contextData, Router router){
            this.router = router;
            this.contextData = contextData;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Request request = Request.create(contextData, req);
            Response response = Response.create(resp.getWriter());
            try {
                router.get(request).execute(request, response);
                //TODO set response charset
                resp.setContentType(response.getContentType());
                resp.setStatus(200);
            }
            catch (HTMLException e){
                new ErrorView(e.getHtmlErrorCode(), e.getErrorTitle(), e.getMessage()).writeTo(resp.getWriter());
                resp.setStatus(e.getHtmlErrorCode());
            }
            catch (Exception e) {
                new ErrorView(500, "Internal Server Error", e.getMessage()).writeTo(resp.getWriter());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    public HttpServlet createHttpServlet(ContextData contextData){
        return new RouterHttpServlet(contextData, this);
    }
}
