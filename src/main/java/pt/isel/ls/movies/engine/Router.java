package pt.isel.ls.movies.engine;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.exceptions.MethodNotAllowedException;
import pt.isel.ls.movies.exceptions.PathNotFoundException;
import pt.isel.ls.movies.exceptions.common.HTMLException;
import pt.isel.ls.movies.view.errors.ErrorView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Class whose instance contains the routing for a set of Commands
 */
public class Router {

    private static Logger logger = LoggerFactory.getLogger(Router.class);

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
    private List<ICommand> commands;

    public Router(){
        methods = new Node();
        commands = new LinkedList<>();
    }

    public Collection<ICommand> getCommands(){
        return commands;
    }

    public void add(String method, String path, ICommand iCommand){
        logger.debug("adding new listener: method: " + method + " path: " + path);
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
        node.setCommand(iCommand);
        commands.add(iCommand);
    }

    public Node addNode(Node node, String name){
        logger.debug("adding node: " + name);
        name = name.toLowerCase();
        Node auxNode = node.get(name);
        if(auxNode == null){
            auxNode = new Node();
            node.nodes.put(name, auxNode);
        }
        return auxNode;
    }

    public Node addParameter(Node node, String name){
        logger.debug("adding parameter: " + name);
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
        Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);

        for(Class<? extends ICommand> aClass : classes ){
            try {
                Command.Creator creator = (Command.Creator)aClass.getField("CREATOR").get(null);
                logger.info("Adding new command: " + creator.details().toString());
                Command command = creator.create(ds);
                Command.CommandDetails details = creator.details();
                router.add(details.method, details.path, command);
            } catch (NoSuchFieldException ex){
                logger.debug(aClass.getName() + " not being added to commands");
            }
        }
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
            doAll(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doAll(req, resp);
        }

        /**
         * Handles all the requests
         *
         * @param req  Request given from http post and http get
         * @param resp Response given from http post and http get
         * @throws IOException todo
         */
        private void doAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            Request request = Request.create(contextData, req);
            Response response = Response.create(resp.getWriter());
            try {
                router.get(request).execute(request, response);
                //TODO set response charset
                if (response.getHeader("Location") != null) {
                    resp.setHeader("Location", response.getHeader("Location"));
                }
                if (response.getContentType() != null) {
                    resp.setContentType(response.getContentType());
                }
                resp.setStatus(response.getStatus());
            } catch (HTMLException e) {
                logger.error("doAll HTMLException", e);
                new ErrorView(e.getHtmlErrorCode(), e.getErrorTitle(), e.getMessage()).writeTo(resp.getWriter());
                resp.setStatus(e.getHtmlErrorCode());
            } catch (Exception e) {
                logger.error("doAll Exception", e);
                new ErrorView(500, "Internal Server Error", e.getMessage()).writeTo(resp.getWriter());
                resp.setStatus(500);
            }
        }
    }

    public HttpServlet createHttpServlet(ContextData contextData){
        return new RouterHttpServlet(contextData, this);
    }
}
