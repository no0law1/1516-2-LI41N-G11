package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.view.home.HomeView;

import javax.sql.DataSource;

/**
 * Command of the Home page
 */
public class Home extends Command.ViewCommand {

    private static final String DETAILS = "Home page of webserver";

    private static final String METHOD = "GET";

    private static final String PATH = "/";

    public Home(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {
        views.put("text/html", new HomeView());
        //TODO: accept plain text?
    }
}
