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
 * Get a single movie instance from the database
 */
public class GetMovie extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        Movie movie;
        int mid = Integer.parseInt(request.getParameter("mid"));

        try(Connection connection = dataSource.getConnection()){
            movie = MovieDAO.getMovie(connection, mid);
        }

        views.put("text/html", new SingleMovieViewHtml(movie));
        views.put("text/plain", new SingleMovieView(movie));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }
}
