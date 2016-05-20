package pt.isel.ls.movies.container.commands.movies.tops;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.view.movie.TopRatingsViewHtml;

import javax.sql.DataSource;

/**
 * Command used to show the list all the options of top rating movies
 */
public class TopRatings extends Command {

    public TopRatings(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        views.put("text/html", new TopRatingsViewHtml());
        //TODO: accept plain text?
    }
}
