package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.movie.SingleMovieView;
import pt.isel.ls.movies.view.movie.SingleMovieViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Get a single movie instance from the database
 */
public class GetMovie extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new GetMovie(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/movies/{mid}", null, "Gets a specific movie");
        }
    };

    public GetMovie(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        Movie movie;
        int mid = request.getIntParameter("mid");

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        int count;
        List<Review> reviews;
        try(Connection connection = dataSource.getConnection()){
            movie = MovieDAO.getMovie(connection, mid);
            reviews = ReviewDAO.getReviews(connection, mid, top, skip);
            count = ReviewDAO.getCount(connection, mid);
        }

        views.put("text/html", new SingleMovieViewHtml(movie, reviews, count, top, skip));
        views.put("text/plain", new SingleMovieView(movie));
    }
}
