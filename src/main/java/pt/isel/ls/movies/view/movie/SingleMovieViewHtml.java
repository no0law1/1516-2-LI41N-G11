package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * Html view of a {@link Movie}
 */
public class SingleMovieViewHtml extends HtmlBootstrap {

    public SingleMovieViewHtml(Movie movie) {
        super(movie.getTitle(),
                h3(text("Movie " + movie.getId())),
                h1(text(movie.getTitle() + " (" + movie.getReleaseYear() + ")")),
                h2(text(movie.getGenre()))
        );
    }

}
