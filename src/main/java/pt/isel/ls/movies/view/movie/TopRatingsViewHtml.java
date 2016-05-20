package pt.isel.ls.movies.view.movie;

import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * View of base page of all top rating movies
 */
public class TopRatingsViewHtml extends HtmlPage {

    public TopRatingsViewHtml() {
        super("Listing Tops",
                table(
                        tr(
                                td(
                                        form("GET", "/tops/5/ratings/higher/average",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Movies with highest average rating")))
                        ),
                        tr(
                                td(
                                        form("GET", "/tops/5/ratings/lower/average",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Movies with lowest average rating")))
                        ),
                        tr(
                                td(
                                        form("GET", "/tops/5/reviews/higher/count",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Most Reviewed Movies")))
                        )
                )
        );
    }

}
