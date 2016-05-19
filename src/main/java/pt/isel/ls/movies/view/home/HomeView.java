package pt.isel.ls.movies.view.home;

import pt.isel.ls.utils.html.HtmlPage;

/**
 * Homepage for the web page
 */
public class HomeView extends HtmlPage {

    public HomeView() {
        super("Welcome",
                h1(text("Welcome to the Movie Database")),
                table(
                        tr(
                                td(a("/collections", "Collections")),
                                td(a("/movies", "Movies")),
                                td(a("/tops/ratings", "Listing Tops"))
                        )
                ),
                p(text("Make yourself at home"))
        );
    }

}
