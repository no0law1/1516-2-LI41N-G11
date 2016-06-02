package pt.isel.ls.movies.container.commands.common;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.ContextData;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.engine.Router;

import javax.sql.DataSource;

public class Listen extends Command {

    private static final String DETAILS = "Launches a webserver listening on port 8080 unless parameter port is specified";

    private static final String METHOD = "LISTEN";

    private static final String PATH = "/";

    public Listen(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {

        if(request.getContextData().runningServer != null){
            System.out.println("A Server is already running");
            return;
        }

        int port = 8080;
        if(request.getParameter("port") != null){
            port = Integer.valueOf(request.getParameter("port"));
        }

        System.out.println(String.format("launching Web server at port %d...", port));
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(Router.createRouter(dataSource).createHttpServlet(request.getContextData())), "/*");
        //ServletHandler handler = new ServletHandler();
        //server.setHandler(handler);
        //handler.addServletWithMapping(new ServletHolder(Router.createRouter(dataSource).createHttpServlet(request.getContextData())), "/*");
        server.start();
        System.out.println("Server is started");

        request.getContextData().runningServer = server;

        if(!request.getContextData().dynamic) {
            request.getContextData().runningServer.join();
        }
    }


    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }

    public static void main(String[] args) throws Exception {
        ContextData data = new ContextData();
        data.dynamic = false;

        String[] request = new String[]{"LISTEN", "/", "port="+System.getenv("PORT")};

        new Listen(DataSourceFactory.createInstance()).doWork(Request.create(data, request));
    }
}