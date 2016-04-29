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
 * Gets all movie instances from the database
 */
public class GetMovieList extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        List<Movie> movies;

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getMovies(
                    connection,
                    Integer.valueOf(request.getParameterOrDefault("top", "-1")),
                    Integer.valueOf(request.getParameterOrDefault("skip", "0"))
            );
        }

        views.put("text/html", new MoviesViewHtml(movies));
        views.put("text/plain", new MoviesView(movies));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }
}
