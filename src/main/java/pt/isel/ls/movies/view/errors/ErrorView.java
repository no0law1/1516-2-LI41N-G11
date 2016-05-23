package pt.isel.ls.movies.view.errors;

import pt.isel.ls.utils.html.HtmlBootstrap;

/**
 * TODO: Commentary.
 */
public class ErrorView extends HtmlBootstrap {

    public ErrorView(int errorCode, String errorTitle, String errorDescription) {
        super(String.format("%d %s", errorCode, errorTitle),
                h1(text(String.format("%d %s", errorCode, errorTitle))),
                p(text(errorDescription))
        );
    }

}
