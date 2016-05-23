package pt.isel.ls.movies.view.errors;

import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * TODO: Commentary.
 */
public class NotFoundHtml extends HtmlBootstrap {

    public NotFoundHtml() {
        super("404 Not Found",
                h1(text("404 Not Found"))
                        .withAttr("class", "text-center")
        );
    }

}
