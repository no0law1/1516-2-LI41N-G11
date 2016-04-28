package pt.isel.ls.movies.view.rating;

import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

import java.util.List;

/**
 * Html view of a set of {@link Rating}
 */
public class RatingsViewHtml implements IView {

    private List<Rating> ratings;

    public RatingsViewHtml(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Ratings"))
                .append(body())
                .append("</html>\n")
                .toString();
    }

    private String body() {
        float average = 0;
        int count = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:50%\" border=\"5\">\n");
        builder.append("<tr>\n<th>Movie ID</th>\n<th>Value</th>\n<th>Count</th>\n</tr>\n");
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            count += rating.getCount();
            builder.append("<tr>\n")
                    .append("\t<td>" + rating.getMid() + "</td>\n")
                    .append("\t<td>" + rating.getVal() + "</td>\n")
                    .append("\t<td>" + rating.getCount() + "</td>\n")
                    .append("</tr>\n");
        }
        builder.append("</table>\n");
        builder.append("<h2>\nAverage = " + average / count + "\n</h2>\n");
        return builder.toString();
    }
}
