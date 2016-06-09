package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.view.home.HomeView;

import javax.sql.DataSource;

/**
 * Command of the Home page
 */
public class Home extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new Home(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/", null, "Home page of webserver");
        }
    };

    public Home(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        views.put("text/html", new HomeView());
        //TODO: accept plain text?
    }
}
