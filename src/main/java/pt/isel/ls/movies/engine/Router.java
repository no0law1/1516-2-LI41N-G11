package pt.isel.ls.movies.engine;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.view.errors.NotFoundHtml;
import pt.isel.ls.utils.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
        String prefix = "pt.isel.ls.movies.container.commands.";
        Map<String, String> map = FileUtils.getFromFile("src/main/res/commands.txt", FileUtils.Option.COMMANDS);
        for (String key : map.keySet()) {
            String[] pathAndMethod = key.split(" ");
            ICommand command = (ICommand) Class.forName(prefix + map.get(key)).getDeclaredConstructor(DataSource.class).newInstance(ds);
            router.add(pathAndMethod[0], pathAndMethod[1], command);
        }
    }

    public class RouterHttpServlet extends HttpServlet{
        protected Router router;

        public RouterHttpServlet(Router router){
            this.router = router;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Request request = Request.create(req);
            Response response = Response.create(resp.getWriter());
            try {
                router.get(request).execute(request, response);
                //TODO set response charset
                resp.setContentType(response.getContentType());
                resp.setStatus(200);
            } catch (Exception e) {
                new NotFoundHtml().writeTo(resp.getWriter());
                resp.setStatus(404);
                e.printStackTrace();
            }
        }
    }

    public HttpServlet createHttpServlet(){
        return new RouterHttpServlet(this);
    }
}
