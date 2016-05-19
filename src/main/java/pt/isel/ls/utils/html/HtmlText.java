package pt.isel.ls.utils.html;

import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;

public class HtmlText implements Writable {

    public final String _text;

    public HtmlText(String text) {
        _text = text;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(_text);
        //w.write(StringEscapeUtils.escapeHtml(_text));
    }
}
