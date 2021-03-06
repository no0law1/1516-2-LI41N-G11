package pt.isel.ls.movies.container.commands.rating;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.rating.RatingsView;
import pt.isel.ls.movies.view.rating.RatingsViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets the average rating and the count to every rating of a Movie
 */
public class GetMovieRating extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new GetMovieRating(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/movies/{mid}/ratings", null, "Gets the rating information for a movie, such as the rating average and the number of votes for each rating value");
        }
    };

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
