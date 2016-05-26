package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.html.*;

import java.util.List;

/**
 * Html view of a set of {@link Review}
 */
public class ReviewsViewHtml extends HtmlBootstrapWithHomeButton {

    public ReviewsViewHtml(int mid, List<Review> reviews, int count, int top, int skip) {
        super("reviews",
                h1(text("Movie Reviews"))
                        .withAttr("class", "text-center"),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("ID")),
                                        th(text("Reviewer")),
                                        th(text("Review Summary")),
                                        th(text("Rating")),
                                        th(text(""))
                                )),
                        getList(reviews)
                )
                        .withAttr("class", "table table-striped"),
                pagingButtons("/movies", null, count, top, skip),
                btnGroupJustified(
                        btnGroup(a("/movies/" + mid + "?top=5", "Movie")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("class", "text-left")
        );
    }

    private static HtmlElem getList(List<Review> reviews) {
        HtmlElem div = new HtmlElem("tbody");
        reviews.forEach(
                review -> div.withContent(
                        tr(
                                td(text(String.valueOf(review.getId()))),
                                td(text(String.valueOf(review.getReviewerName()))),
                                td(text(String.valueOf(review.getReviewSummary()))),
                                td(text(String.valueOf(review.getRating()))),
                                td(
                                        btn(a("/movies/" + review.getMid() + "/reviews/" + review.getId(), "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return div;
    }

}
