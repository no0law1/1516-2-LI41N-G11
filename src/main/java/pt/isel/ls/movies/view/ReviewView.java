package pt.isel.ls.movies.view;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a Review that knows how to draw itself.
 */
public class ReviewView implements IView {

    private int mid;
    private int id;
    private String reviewerName;
    private String reviewSummary;
    private String review;
    private int rating;

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
    public void show() {
        System.out.printf("%s: %s\n", "mid", mid);
        System.out.printf("%s: %s\n", "id", id);
        System.out.printf("%s: %s\n", "Reviewer Name", reviewerName);
        System.out.printf("%s: %s\n", "Review Summary", reviewSummary);
        System.out.printf("%s: %s\n", "Review", review);
        System.out.printf("%s: %s\n", "Rating", rating);
        System.out.println();
    }
}
