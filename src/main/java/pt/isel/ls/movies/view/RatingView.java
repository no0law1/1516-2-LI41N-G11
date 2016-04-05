package pt.isel.ls.movies.view;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.common.IView;

/**
 * TODO: Commentary.
 */
public class RatingView implements IView {

    private int mid;

    private int val;

    private int count;

    public RatingView(int mid, int val) {
        this(mid, val, 1);
    }

    public RatingView(int mid, int val, int count) {
        this.mid = mid;
        this.val = val;
        this.count = count;
    }

    public RatingView(Rating rating) {
        this(rating.getMid(), rating.getVal(), rating.getCount());
    }

    @Override
    public void show() {
        System.out.printf("%s: %s\n", "mid", mid);
        System.out.printf("%s: %s\n", "val", val);
        System.out.printf("%s: %s\n", "count", count);
    }
}
