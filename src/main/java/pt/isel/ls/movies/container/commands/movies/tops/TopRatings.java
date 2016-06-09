package pt.isel.ls.movies.container.commands.movies.tops;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.view.movie.TopRatingsViewHtml;

import javax.sql.DataSource;

/**
 * Command used to show the list all the options of top rating movies
 */
public class TopRatings extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new TopRatings(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/tops/ratings", null, "Base page of all the tops");
        }
    };

    public TopRatings(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        views.put("text/html", new TopRatingsViewHtml());
        //TODO: accept plain text?
    }
}
