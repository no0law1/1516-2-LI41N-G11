package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;

/**
 * Html view of a {@link Review}
 */
public class SingleReviewViewHtml extends HtmlBootstrapWithHomeButton {

    public SingleReviewViewHtml(Review review) {
        super("Review",
                h1(text("Movie " + review.getMid()))
                        .withAttr("class", "text-center"),
                h2(text("<b>Reviewer:</b> " + review.getReviewerName())),
                p(text(review.getReview())),
                h5(text("<b>Rating:</b> " + review.getRating())),
                btnGroupJustified(
                        btnGroup(
                                a("/movies/" + review.getMid() + "?top=5", "Movie")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(text("")),
                        btnGroup(
                                a("/movies/" + review.getMid() + "/reviews?top=5", "All Reviews")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("class", "text-left")
        );
    }

}
