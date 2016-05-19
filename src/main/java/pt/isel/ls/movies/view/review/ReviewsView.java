package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Plain text view of a set of {@link Review}
 */
public class ReviewsView implements Writable {

    private StringBuilder builder;

    public ReviewsView(List<Review> reviews) {
        builder = new StringBuilder();
        if (reviews.isEmpty()) {
            builder.append("There are no Reviews\n");
        }
        reviews.forEach(review -> builder.append(new SingleReviewView(review).getView()));
    }

    public String getView() {
        return builder.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
