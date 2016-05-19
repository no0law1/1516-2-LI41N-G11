package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;

/**
 * Class whose instance represents a review that knows how to draw itself.
 */
public class SingleReviewView implements Writable {

    private StringBuffer view;

    public SingleReviewView(int mid, int id, String reviewerName, String reviewSummary, String review, int rating) {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "mid", mid));
        view.append(String.format("%s: %s\n", "id", id));
        view.append(String.format("%s: %s\n", "Reviewer Name", reviewerName));
        view.append(String.format("%s: %s\n", "review Summary", reviewSummary));
        view.append(String.format("%s: %s\n", "review", review));
        view.append(String.format("%s: %s\n", "rating", rating));
        view.append("\n");
    }

    public SingleReviewView(Review review) {
        this(review.getMid(), review.getId(), review.getReviewerName(),
                review.getReviewSummary(), review.getReview(), review.getRating());
    }

    public String getView() {
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
