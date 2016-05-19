package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.view.home.HomeView;

import javax.sql.DataSource;

/**
 * TODO: Commentary.
 */
public class Home extends Command {

    public Home(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        //TODO: accept plain text?
        new HomeView().writeTo(response.getWriter());
    }
}
