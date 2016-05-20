package pt.isel.ls.movies.view.home;

import pt.isel.ls.utils.html.HtmlElem;
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
                                td(
                                        form("GET", "/collections",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Collections"))),
                                td(
                                        form("GET", "/movies",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Movies"))),
                                td(
                                        form("GET", "/tops/ratings",
                                                new HtmlElem("input")
                                                        .withAttr("type", "submit")
                                                        .withAttr("value", "Listing Tops")))
                        )
                ),
                p(text("Make yourself at home"))
        );
    }

}
