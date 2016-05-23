package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a {@link Movie}
 */
public class SingleMovieViewHtml extends HtmlBootstrap {

    public SingleMovieViewHtml(Movie movie) {
        super(movie.getTitle(),
                h1(text(movie.getTitle()))
                        .withAttr("class", "text-center"),
                h3(text("<b>Release Year:</b> " + movie.getReleaseYear()))
                        .withAttr("style", "width:80%;margin:20px auto;"),
                h3(text("<b>Genre:</b> " + movie.getGenre()))
                        .withAttr("style", "width:80%;margin:20px auto;"),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("ID")),
                                        th(text("Reviewer")),
                                        th(text("Review Summary")),
                                        th(text("Rating"))
                                )
                        ),
                        getList(movie.getReviews())
                )
                        .withAttr("class", "table table-striped")
                        .withAttr("style", "width:80%;margin:20px auto;"),
                btnGroupJustified(
                        btnGroup(
                                a("/movies", "Movies")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-left"),
                        btnGroup(text("")),
                        btnGroup(
                                a("/movies/" + movie.getId() + "/ratings", "Ratings")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-right")
                ).withAttr("style", "width:80%;margin:20px auto;")
        );
    }

    private static Writable getList(List<Review> reviews) {
        HtmlElem div = new HtmlElem("tbody");
        reviews.forEach(
                review -> div.withContent(
                        tr(
                                td(text("Review: " + review.getId())),
                                td(text(review.getReviewerName())),
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
