package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Plain text view of a set of {@link Rating}
 */
public class RatingsView implements Writable {

    private StringBuilder builder;

    public RatingsView(List<Rating> ratings) {
        builder = new StringBuilder();
        if (ratings.isEmpty()) {
            builder.append("There are no Ratings\n");
        }
        float average = 0;
        int count = 0;
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            count += rating.getCount();
            builder.append(new SingleRatingView(rating).getView());
        }
        builder.append(String.format("Average: %s\n", average / count));
    }

    public String getView() {
        return builder.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
