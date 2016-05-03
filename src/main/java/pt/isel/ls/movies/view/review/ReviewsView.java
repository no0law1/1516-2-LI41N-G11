package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.common.IView;

import java.util.List;

/**
 * Plain text view of a set of {@link Review}
 */
public class ReviewsView implements IView {

    private List<Review> reviews;

    public ReviewsView(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String getView() {
        if (reviews.isEmpty()) {
            return "There are no Reviews\n";
        }
        StringBuilder builder = new StringBuilder();

        reviews.forEach(review -> builder.append(new SingleReviewView(review).getView()));

        return builder.toString();
    }
}
