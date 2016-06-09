package pt.isel.ls.movies.container.commands.movies.tops;

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
 * Command that gets the movie with the lowest average rating.
 */
public class GetLowestRatedMovie extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new GetLowestRatedMovie(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/tops/ratings/lower/average", null, "Gets the movie with the lowest average rating");
        }
    };

    public GetLowestRatedMovie(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        Movie movie;

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        int count;
        List<Review> reviews;
        try (Connection connection = dataSource.getConnection()) {
            movie = MovieDAO.getLowestRatingMovie(connection);
            reviews = ReviewDAO.getReviews(connection, movie.getId(), top, skip);
            count = ReviewDAO.getCount(connection, movie.getId());
        }

        views.put("text/html", new SingleMovieViewHtml(movie, reviews, count, top, skip));
        views.put("text/plain", new SingleMovieView(movie));
    }

}
