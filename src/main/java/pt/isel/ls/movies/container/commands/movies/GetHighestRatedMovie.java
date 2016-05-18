package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.SingleMovieView;
import pt.isel.ls.movies.view.movie.SingleMovieViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Command that gets the movie with the highest average rating.
 */
public class GetHighestRatedMovie extends Command {

    public GetHighestRatedMovie(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        Movie movie;

        try (Connection connection = dataSource.getConnection()) {
            movie = MovieDAO.getHighestRatingMovie(connection);
        }

        views.put("text/html", new SingleMovieViewHtml(movie));
        views.put("text/plain", new SingleMovieView(movie));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }
}
