package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.RatingView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets the average Rating and the count to every rating of a Movie
 */
public class GetMovieRating implements ICommand{

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            int mid = Integer.parseInt(request.getQueryParams().get("mid"));
            List<Rating> ratings = RatingDAO.getMovieRatings(connection, mid);

            //TODO: Show Average

            for (Rating rating : ratings) {
                new RatingView(rating).show();
            }
        }
    }
}
