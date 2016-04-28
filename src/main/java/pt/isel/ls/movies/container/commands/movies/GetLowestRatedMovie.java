package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.SingleMovieView;
import pt.isel.ls.movies.view.movie.SingleMovieViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Command that gets the movie with the lowest average rating.
 */
public class GetLowestRatedMovie extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Movie movie;

        try (Connection connection = dataSource.getConnection()) {
            movie = MovieDAO.getLowestRatingMovie(connection);
        }

        views.put("text/html", new SingleMovieViewHtml(movie));
        views.put("text/plain", new SingleMovieView(movie));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        System.out.println(getView(request.getHeaderOrDefault("accept", "text/html")));
    }

}
