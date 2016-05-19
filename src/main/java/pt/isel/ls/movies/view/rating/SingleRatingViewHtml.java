package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * Html view of a {@link Rating}
 */
public class SingleRatingViewHtml extends Html {

    public SingleRatingViewHtml(Rating rating) {
        super(new HtmlPage("Movie " + rating.getMid(),
                h1(text("Rating: " + rating.getVal())),
                h2(text("Count: " + rating.getCount()))
        ));
    }
}
