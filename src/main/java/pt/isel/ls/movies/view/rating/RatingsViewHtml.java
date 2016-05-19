package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

import java.util.List;

/**
 * Html view of a set of {@link Rating}
 */
public class RatingsViewHtml extends Html {

    private float average = 0;
    private int count = 0;

    public RatingsViewHtml(List<Rating> ratings) {
        HtmlElem div = new HtmlElem("div");
        ratings.forEach(
                rating -> {
                    average += rating.getVal() * rating.getCount();
                    count += rating.getCount();
                    div.withContent(
                            tr(
                                    td(text(String.valueOf(rating.getMid()))),
                                    td(text(String.valueOf(rating.getVal()))),
                                    td(text(String.valueOf(rating.getCount())))
                            ));
                }
        );
        HtmlPage page = new HtmlPage("Ratings",
                table(tr(
                        th(text("Movie ID")), th(text("Value")), th(text("Count"))
                        ),
                        div,
                        h2(text("Average = " + average / count))
                )
        );
        _content = new CompositeWritable(page);
    }
}
