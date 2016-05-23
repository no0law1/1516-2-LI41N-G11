package pt.isel.ls.movies.view.home;

import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * Homepage for the web page
 */
public class HomeView extends HtmlBootstrap {

    public HomeView() {
        super("Welcome",
                h1(text("Welcome to the Movie Database"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                btnGroupJustified(
                        btnGroup(a("/collections", "Collections")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/movies", "Movies")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/tops/ratings", "Listing Tops")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("style", "width:80%;margin:20px auto;"),
                p(text("Make yourself at home"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px")
        );
    }

}
