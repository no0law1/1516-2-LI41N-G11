package pt.isel.ls.movies.container.commands.common;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

public class Listen extends Command {

    private static Logger logger = LoggerFactory.getLogger(Listen.class);

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new Listen(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("LISTEN", "/", null, "Launches a webserver listening on port 8080 unless parameter port is specified");
        }
    };

    public Listen(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {

        if(request.getContextData().runningServer != null){
            logger.info("A Server is already running");
            return;
        }

        int port = 8080;
        if(request.getParameter("port") != null){
            port = Integer.valueOf(request.getParameter("port"));
        }

        logger.info(String.format("launching Web server at port %d...", port));
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(request.getContextData().router.createHttpServlet(request.getContextData())), "/*");
        server.start();
        logger.info("Server is started");

        request.getContextData().runningServer = server;

        if(!request.getContextData().dynamic) {
            request.getContextData().runningServer.join();
        }
    }
}