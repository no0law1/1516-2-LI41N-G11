package pt.isel.ls.movies.view.movie;

import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;

/**
 * View of base page of all top rating movies
 */
public class TopRatingsViewHtml extends HtmlBootstrapWithHomeButton {

    public TopRatingsViewHtml() {
        super("Listing Tops",
                h1(text("Tops")).withAttr("class", "text-center"),
                btnGroupJustified(
                        btnGroup(a("/tops/5/ratings/higher/average", "Movies with highest average rating")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/tops/5/ratings/lower/average", "Movies with lowest average rating")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/tops/5/reviews/higher/count", "Most Reviewed Movies")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ),
                btnGroupJustified(
                        btnGroup(
                                a("/movies", "Movies")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-left")
                                .withAttr("style", "padding")
                )
        );
    }

}
