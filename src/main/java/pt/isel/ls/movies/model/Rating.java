package pt.isel.ls.movies.model;

/**
 * Class whose instance represents a rating value count of a movie
 */
public class Rating {

    private int mid;

    private int val;

    private int count;

    public Rating(int mid, int val){
        this(mid, val, 1);
    }

    public Rating(int mid, int val, int count){
        this.mid = mid;
        this.val = val;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getMid() {
        return mid;
    }

    public int getVal() {
        return val;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Rating)){
            return false;
        }
        Rating rating = (Rating)o;
        return this.mid == rating.mid && this.val == rating.val && this.count == rating.count;
    }
}
