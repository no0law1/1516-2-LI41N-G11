package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.movies.view.movie.MoviesViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Command that gets the n movies with the lowest average rating.
 */
public class GetLowestRatedMovies extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        List<Movie> movies;
        int n = Integer.parseInt(request.getParameter("n"));

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getLowestRatingMovies(connection, n);
        }

        views.put("text/html", new MoviesViewHtml(movies));
        views.put("text/plain", new MoviesView(movies));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }

}
