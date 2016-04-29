package pt.isel.ls.movies.view.review;

import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

import java.util.List;

/**
 * Html view of a set of {@link Review}
 */
public class ReviewsViewHtml implements IView {

    private List<Review> reviews;

    public ReviewsViewHtml(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Reviews"))
                .append("<body>\n")
                .append(body())
                .append("</body>\n")
                .append("</html>\n")
                .toString();
    }

    private String body() {
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:50%\" border=\"5\">\n");
        builder.append("<tr>\n<th>ID</th>\n<th>Reviewer</th>\n<th>Review</th>\n<th>Rating</th>\n</tr>\n");
        reviews.forEach(
                review -> builder.append("<tr>\n")
                        .append("\t<td>" + review.getId() + "</td>\n")
                        .append("\t<td>" + review.getReviewerName() + "</td>\n")
                        .append("\t<td>" + review.getReview() + "</td>\n")
                        .append("\t<td>" + review.getRating() + "</td>\n")
                        .append("</tr>\n"));
        builder.append("</table>\n");
        return builder.toString();
    }
}
