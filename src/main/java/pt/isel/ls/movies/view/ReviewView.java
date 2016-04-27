package pt.isel.ls.movies.view;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a review that knows how to draw itself.
 */
public class ReviewView implements IView {

    private int mid;
    private int id;
    private String reviewerName;
    private String reviewSummary;
    private String review;
    private int rating;

    private StringBuffer view;

    public ReviewView(int mid, int id, String reviewerName, String reviewSummary, String review, int rating) {
        this.mid = mid;
        this.id = id;
        this.reviewerName = reviewerName;
        this.reviewSummary = reviewSummary;
        this.review = review;
        this.rating = rating;
    }

    public ReviewView(Review review) {
        this(review.getMid(),
                review.getId(),
                review.getReviewerName(),
                review.getReviewSummary(),
                review.getReview(),
                review.getRating());
    }

    @Override
    public String getView() {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "mid", mid));
        view.append(String.format("%s: %s\n", "id", id));
        view.append(String.format("%s: %s\n", "Reviewer Name", reviewerName));
        view.append(String.format("%s: %s\n", "review Summary", reviewSummary));
        view.append(String.format("%s: %s\n", "review", review));
        view.append(String.format("%s: %s\n", "rating", rating));
        view.append("\n");
        return view.toString();
    }
}
