package pt.isel.ls.utils.html;

import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.http.HttpContent;

import java.io.IOException;
import java.io.Writer;

public class Html implements HttpContent {

    protected Writable _content;

    protected Html(Writable... cs) {
        _content = new CompositeWritable(cs);
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        _content.writeTo(w);
    }

    @Override
    public String getMediaType() {
        return "text/html";
    }

    public static Writable text(String s) {
        return new HtmlText(s);
    }

    public static HtmlElem h1(Writable... c) {
        return new HtmlElem("h1", c);
    }

    public static HtmlElem h2(Writable... c) {
        return new HtmlElem("h2", c);
    }

    public static HtmlElem h3(Writable... c) {
        return new HtmlElem("h3", c);
    }

    public static Writable h4(Writable... c) {
        return new HtmlElem("h4", c);
    }

    public static HtmlElem h5(Writable... c) {
        return new HtmlElem("h5", c);
    }

    public static HtmlElem p(Writable... c) {
        return new HtmlElem("p", c);
    }

    public static HtmlElem form(String method, String url, Writable... c) {
        return new HtmlElem("form", c)
                .withAttr("method", method)
                .withAttr("action", url);
    }

    public static Writable label(String to, String text) {
        return new HtmlElem("label", new HtmlText(text))
                .withAttr("for", to);
    }

    public static Writable textInput(String name) {
        return new HtmlElem("input")
                .withAttr("type", "text")
                .withAttr("name", name);
    }

    public static HtmlElem table(Writable... c) {
        return new HtmlElem("table", c);
    }

    public static HtmlElem btn(Writable... c) {
        return new HtmlElem("button", c);
    }

    public static Writable tr(Writable... c) {
        return new HtmlElem("tr", c);
    }

    public static HtmlElem th(Writable... c) {
        return new HtmlElem("th", c);
    }

    public static HtmlElem td(Writable... c) {
        return new HtmlElem("td", c);
    }

    public static Writable ul(Writable... c) {
        return new HtmlElem("ul", c);
    }

    public static Writable li(Writable... c) {
        return new HtmlElem("li", c);
    }

    public static HtmlElem a(String href, String t) {
        return new HtmlElem("a", text(t))
                .withAttr("href", href);
    }

    public static Writable css(String link) {
        return new HtmlElem("link").withAttr("rel", "stylesheet").withAttr("href", link);
    }
}
