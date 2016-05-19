package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * Html view of a {@link Review}
 */
public class SingleReviewViewHtml extends Html {

    public SingleReviewViewHtml(Review review) {
        super(new HtmlPage("Review",
                h1(text("Movie " + review.getMid())),
                h2(text(review.getReviewerName())),
                p(text(review.getReview())),
                h5(text(String.valueOf(review.getRating())))
        ));
    }

}
