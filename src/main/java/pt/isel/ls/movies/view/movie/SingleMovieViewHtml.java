package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * Html view of a {@link Movie}
 */
public class SingleMovieViewHtml extends Html {

    public SingleMovieViewHtml(Movie movie) {
        super(new HtmlPage("Movie " + movie.getId(),
                h3(text(String.valueOf(movie.getId()))),
                h1(text(movie.getTitle() + " (" + movie.getReleaseYear() + ")")),
                h2(text(movie.getGenre())))
        );
    }

}
