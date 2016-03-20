package pt.isel.ls.movies.model;

/**
 * Class whose instance represent a Movie Review
 */
public class Review {

    public int mid;
    public int id;
    public String reviewerName;
    public String review;
    public int rating;

    public Review(int mid, int id, String reviewerName, String review, int rating){
        this.mid = mid;
        this.id = id;
        this.reviewerName = reviewerName;
        this.review = review;
        this.rating = rating;
    }
}
