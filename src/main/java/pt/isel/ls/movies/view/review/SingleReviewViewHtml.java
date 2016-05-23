package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * Html view of a {@link Review}
 */
public class SingleReviewViewHtml extends HtmlBootstrap {

    public SingleReviewViewHtml(Review review) {
        super("Review",
                h1(text("Movie " + review.getMid()))
                        .withAttr("class", "text-center"),
                h2(text("<b>Reviewer:</b> " + review.getReviewerName()))
                        .withAttr("style", "width:80%;margin:20px auto;"),
                p(text(review.getReview()))
                        .withAttr("style", "width:80%;margin:20px auto;"),
                h5(text("<b>Rating:</b> " + review.getRating()))
                        .withAttr("style", "width:80%;margin:20px auto;"),
                btnGroupJustified(
                        btnGroup(
                                a("/movies/" + review.getMid(), "Movie")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-left"),
                        btnGroup(text("")),
                        btnGroup(
                                a("/movies/" + review.getMid() + "/reviews", "All Reviews")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-right")
                ).withAttr("style", "width:60%;margin:20px auto;")
        );
    }

}
