package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

import java.util.List;

/**
 * Html view of a set of {@link Review}
 */
public class ReviewsViewHtml extends Html {

    //TODO: Change to bootstrap
    public ReviewsViewHtml(List<Review> reviews) {
        HtmlElem div = new HtmlElem("div");
        reviews.forEach(
                review -> div.withContent(
                        tr(
                                td(a("/movies/" + review.getMid() + "/reviews/" + review.getId(), "Review: " + review.getId())),
                                td(text(review.getReviewerName())),
                                td(text(String.valueOf(review.getReview()))),
                                td(text(String.valueOf(review.getRating())))
                        ))
        );
        HtmlPage page = new HtmlPage("Reviews",
                table(tr(
                        th(text("ID")), th(text("Reviewer")), th(text("Review")), th(text("Rating"))
                        ), div
                ).withAttr("style", "width:50%").withAttr("border", "5")
        );
        _content = new CompositeWritable(page);
    }

}
