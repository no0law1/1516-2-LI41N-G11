package pt.isel.ls.movies.container.commands.rating;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.rating.RatingsView;
import pt.isel.ls.movies.view.rating.RatingsViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets the average rating and the count to every rating of a Movie
 */
public class GetMovieRating extends Command {

    public GetMovieRating(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Rating> ratings;
        int mid = request.getIntParameter("mid");

        try (Connection connection = dataSource.getConnection()) {
            ratings = RatingDAO.getMovieRatings(connection, mid);
        }

        views.put("text/html", new RatingsViewHtml(mid, ratings));
        views.put("text/plain", new RatingsView(ratings));
    }
}
