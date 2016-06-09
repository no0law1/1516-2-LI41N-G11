package pt.isel.ls.movies.container.commands.movies.tops;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.movies.view.movie.MoviesViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Command that gets the n movies with the lowest average rating.
 */
public class GetLowestRatedMovies extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new GetLowestRatedMovies(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/tops/{n}/ratings/lower/average", null, "Gets the n movies with the lowest average rating");
        }
    };

    public GetLowestRatedMovies(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Movie> movies;
        int n = request.getIntParameter("n");

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getLowestRatingMovies(connection, n);
        }

        views.put("text/html", new MoviesViewHtml(" - " + n + " Lowest Rated Movies", movies, n, -1, 0, null));
        views.put("text/plain", new MoviesView(movies));
    }

}
