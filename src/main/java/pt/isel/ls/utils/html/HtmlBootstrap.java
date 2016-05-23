package pt.isel.ls.utils.html;

import pt.isel.ls.utils.common.Writable;


public class HtmlBootstrap extends Html {

    public HtmlBootstrap(String title, Writable... cs) {
        super(
                new HtmlElem("html",
                        head(title),
                        new HtmlElem("body", cs)
                )
        );
    }

    protected static Writable head(String title) {
        Writable t = new HtmlElem("title", text(title));
        Writable bootstrap = css("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css");
        Writable bootstrapTheme = css("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css");

        return new HtmlElem("head", t, bootstrap, bootstrapTheme);
    }

    public static HtmlElem btnGroupJustified(Writable... content) {
        return new HtmlElem("div", content).withAttr("class", "btn-group btn-group-justified");
    }

    public static HtmlElem btnGroup(Writable... content) {
        return new HtmlElem("div", content)
                .withAttr("class", "btn-group")
                .withAttr("role", "group");
    }
}
