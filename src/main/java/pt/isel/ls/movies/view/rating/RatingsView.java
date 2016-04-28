package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.common.IView;

import java.util.List;

/**
 * Plain text view of a set of {@link Rating}
 */
public class RatingsView implements IView {

    private List<Rating> ratings;

    public RatingsView(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String getView() {
        StringBuilder view = new StringBuilder();
        float average = 0;
        int count = 0;
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            count += rating.getCount();
            view.append(new SingleRatingView(rating).getView());
        }
        view.append(String.format("Average: %s\n", average / count));
        return view.toString();
    }
}
