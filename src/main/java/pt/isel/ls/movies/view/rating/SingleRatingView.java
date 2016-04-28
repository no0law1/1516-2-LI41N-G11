package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a rating that knows how to draw itself.
 */
public class SingleRatingView implements IView {

    private int mid;

    private int val;

    private int count;

    private StringBuffer view;

    public SingleRatingView(int mid, int val) {
        this(mid, val, 1);
    }

    public SingleRatingView(int mid, int val, int count) {
        this.mid = mid;
        this.val = val;
        this.count = count;
    }

    public SingleRatingView(Rating rating) {
        this(rating.getMid(), rating.getVal(), rating.getCount());
    }


    @Override
    public String getView() {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "mid", mid));
        view.append(String.format("%s: %s\n", "val", val));
        view.append(String.format("%s: %s\n", "count", count));
        view.append("\n");
        return view.toString();
    }
}
