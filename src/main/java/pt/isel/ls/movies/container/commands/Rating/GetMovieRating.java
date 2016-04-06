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
        List<Rating> ratings;
        int mid = Integer.parseInt(request.get("mid"));

        try (Connection connection = dataSource.getConnection()) {
            ratings = RatingDAO.getMovieRatings(connection, mid);
        }

        float average = 0;
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            new RatingView(rating).show();
        }
        //TODO: Improve
        System.out.printf("Average: %s", average / ratings.size());
    }
}
