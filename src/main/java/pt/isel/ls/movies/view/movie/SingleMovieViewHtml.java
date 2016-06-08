package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a {@link Movie}
 */
public class SingleMovieViewHtml extends HtmlBootstrapWithHomeButton {

    public SingleMovieViewHtml(Movie movie, List<Review> reviews, int count, int top, int skip) {
        super(movie.getTitle(),
                h1(text(movie.getTitle()))
                        .withAttr("class", "text-center"),
                h3(text("<b>Release Year:</b> " + movie.getReleaseYear())),
                h3(text("<b>Genre:</b> " + movie.getGenre())),
                getList(reviews),
                pagingButtons("/movies/" + movie.getId(), null, count, top, skip),
                h2(text("Add a review"))
                        .withAttr("class", "text-center"),
                form("POST", "/movies/" + movie.getId() + "/reviews",
                        formGroup("name", "Name", "name"),
                        formGroup("review", "Review", "review"),
                        formGroup("summary", "Summary", "reviewSummary"),
                        formGroup("rating", "Rating", "rating"),
                        div(
                                new HtmlElem("input")
                                        .withAttr("class", "btn btn-default")
                                        .withAttr("type", "submit")
                                        .withAttr("name", "Submit")
                                        .withAttr("style", "margin-left:10px")
                        ).withAttr("class", "col-sm-10 text-right")
                ).withAttr("class", "form-horizontal clearfix"),
                btnGroupJustified(
                        btnGroup(
                                a("/movies?top=5", "Movies")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(text("")),
                        btnGroup(
                                a("/movies/" + movie.getId() + "/ratings", "Ratings")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("class", "text-left")
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
                                        btn(a("/movies/" + review.getMid() + "/reviews/" + review.getId() + "?top=5", "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return table(
                new HtmlElem("thead",
                        tr(
                                th(text("ID")),
                                th(text("Reviewer")),
                                th(text("Review Summary")),
                                th(text("Rating"))
                        )
                ),
                div
        ).withAttr("class", "table table-striped");
    }

}
