package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a set of {@link Rating}
 */
public class RatingsViewHtml extends HtmlBootstrapWithHomeButton {

    public RatingsViewHtml(int mid, List<Rating> ratings) {
        super("Ratings",
                h1(text("Movie Ratings"))
                        .withAttr("class", "text-center"),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("Movie ID")),
                                        th(text("Value")),
                                        th(text("Count"))
                                )),
                        getList(ratings)
                )
                        .withAttr("class", "table table-striped"),
                h2(text("<b>Average:</b> " + getAverage(ratings))),
                btnGroupJustified(
                        btnGroup(
                                a("/movies/" + mid + "?top=5", "Movie")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("class", "text-left")
        );
    }

    private static float getAverage(List<Rating> ratings) {
        float average = 0, count = 0;
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            count += rating.getCount();
        }
        return average / count;
    }

    private static HtmlElem getList(List<Rating> ratings) {
        HtmlElem div = new HtmlElem("tbody");
        ratings.forEach(
                rating -> div.withContent(
                        tr(
                                td(text(String.valueOf(rating.getMid()))),
                                td(text(String.valueOf(rating.getVal()))),
                                td(text(String.valueOf(rating.getCount())))
                        ))
        );
        return div;
    }
}
