package pt.isel.ls.utils.html;

import pt.isel.ls.utils.common.Writable;


public class HtmlBootstrap extends Html {

    public HtmlBootstrap(String title, Writable... cs) {
        super(
                new HtmlElem("html",
                        head(title),
                        new HtmlElem("body",
                                container(cs)
                        )
                )
        );
    }

    protected static Writable head(String title) {
        Writable t = new HtmlElem("title", text(title));
        Writable bootstrap = css("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css");
        Writable bootstrapTheme = css("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css");

        return new HtmlElem("head", t, bootstrap, bootstrapTheme, style());
    }

    public static HtmlElem pagingButtons(String path, String parameters, int count, int top, int skip){
        if(top == -1 || (skip <= 0 && count <= skip+top) ) return btnGroup(text(""));
        String mpath = path + "?top="+top;
        if(parameters != null){
            mpath += "&" + parameters;
        }
        if(skip <= 0 && count > skip+top) {
            return btnGroupJustified(
                    btnGroup( a(mpath + "&skip=" + (skip+top), ">").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                    btnGroup(text("")),
                    btnGroup( a(mpath + "&skip=" + (count-(count%top == 0 ? 5 : count%top)), ">>").withAttr("role", "btn").withAttr("class", "btn btn-default"))
            ).withAttr("class", "text-right");
        }
        if(skip > 0 && count <= skip+top) {
            return btnGroupJustified(
                    btnGroup( a(mpath + "&skip=0", "<<").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                    btnGroup(text("")),
                    btnGroup( a(mpath + "&skip=" + (skip-top < 0 ? 0 : skip-top), "<").withAttr("role", "btn").withAttr("class", "btn btn-default"))
            ).withAttr("class", "text-right");
        }
        return btnGroupJustified(
                btnGroup( a(mpath + "&skip=0", "<<").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                btnGroup(text("")),
                btnGroup( a(mpath + "&skip=" + (skip-top < 0 ? 0 : skip-top), "<").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                btnGroup(text("")),
                btnGroup( a(mpath + "&skip=" + (skip+top), ">").withAttr("role", "btn").withAttr("class", "btn btn-default")),
                btnGroup(text("")),
                btnGroup( a(mpath + "&skip=" + (count-(count%top == 0 ? 5 : count%top)), ">>").withAttr("role", "btn").withAttr("class", "btn btn-default"))
        ).withAttr("class", "text-right");
    }

    public static HtmlElem btnGroupJustified(Writable... content) {
        return new HtmlElem("div", content).withAttr("class", "btn-group btn-group-justified").withAttr("style", "margin-top: 10px");
    }

    public static HtmlElem container(Writable... content) {
        return new HtmlElem("div",
                new HtmlElem("div", new HtmlElem("div", content).withAttr("class", "col-md-12"))
                        .withAttr("class", "row"))
                .withAttr("class", "container");
    }

    public static HtmlElem btnGroup(Writable... content) {
        return new HtmlElem("div", content)
                .withAttr("class", "btn-group")
                .withAttr("role", "group");
    }
}
