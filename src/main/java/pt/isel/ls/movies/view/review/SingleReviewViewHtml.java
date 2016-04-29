package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

/**
 * Html view of a {@link Review}
 */
public class SingleReviewViewHtml implements IView {

    private Review review;

    public SingleReviewViewHtml(Review review) {
        this.review = review;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Review"))
                .append("<body>\n")
                .append(body())
                .append("</body>\n")
                .append("</html>\n")
                .toString();
    }

    private String body() {
        return new StringBuilder()
                .append("\t<h1>Movie " + review.getMid() + "</h1>\n")
                .append("\t<h2>" + review.getReviewerName() + "</h2>\n")
                .append("\t<p>" + review.getReview() + "</p>\n")
                .append("\t<h5>" + review.getRating() + "/5</h5>\n")
                .toString();
    }
}
