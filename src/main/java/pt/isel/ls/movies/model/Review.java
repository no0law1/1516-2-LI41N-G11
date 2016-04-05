package pt.isel.ls.movies.model;

/**
 * Class whose instance represent a Movie Review
 */
public class Review {

    public static class ReviewUID{

        public int mid;
        public int id;

        public ReviewUID(int mid, int id){
            this.mid = mid;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof ReviewUID)){
                return false;
            }
            ReviewUID reviewUID = (ReviewUID)o;
            return this.mid == reviewUID.mid && this.id == reviewUID.id;
        }
    }

    private int mid;
    private int id;
    private String reviewerName;
    private String reviewSummary;
    private String review;
    private int rating;

    public Review(int mid, int id, String reviewerName, String reviewSummary, String review, int rating){
        this.mid = mid;
        this.id = id;
        this.reviewerName = reviewerName;
        this.reviewSummary = reviewSummary;
        this.review = review;
        this.rating = rating;
    }

    public int getMid() {
        return mid;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Review)){
            return false;
        }
        Review review = (Review)o;
        return this.mid == review.mid &&
                this.id == review.id &&
                this.reviewerName != null && this.reviewerName.equals(review.reviewerName) &&
                this.reviewSummary != null && this.reviewSummary.equals(review.reviewSummary) &&
                (this.review==null && review.review==null || this.review!=null && this.review.equals(review.review)) &&
                this.rating == review.rating;
    }
}
