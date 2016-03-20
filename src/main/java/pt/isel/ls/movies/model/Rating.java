package pt.isel.ls.movies.model;

/**
 * Class whose instance represents a rating value count of a movie
 */
public class Rating {
    public int mid;
    public int val;
    public int count;

    public Rating(int mid, int val, int count){
        this.mid = mid;
        this.val = val;
        this.count = count;
    }
}
