package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;

/**
 * Class whose instance represents a rating that knows how to draw itself.
 */
public class SingleRatingView implements Writable {

    private StringBuffer view;

    public SingleRatingView(int mid, int val) {
        this(mid, val, 1);
    }

    public SingleRatingView(Rating rating) {
        this(rating.getMid(), rating.getVal(), rating.getCount());
    }

    public SingleRatingView(int mid, int val, int count) {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "mid", mid));
        view.append(String.format("%s: %s\n", "val", val));
        view.append(String.format("%s: %s\n", "count", count));
        view.append("\n");
    }

    public String getView() {
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
