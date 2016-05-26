package pt.isel.ls.movies.view.home;

import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * Homepage for the web page
 */
public class HomeView extends HtmlBootstrap {

    public HomeView() {
        super("Welcome",
                h1(text("Movie Database"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                btnGroupJustified(
                        btnGroup(a("/collections?top=5", "Collections").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/movies?top=5", "Movies").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                        btnGroup(a("/tops/ratings", "Tops").withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ),
                p(text("Make yourself at home"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px")
        );
    }

}
