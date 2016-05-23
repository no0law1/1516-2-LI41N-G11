package pt.isel.ls.utils.html;

import pt.isel.ls.utils.common.Writable;


public class HtmlBootstrapWithHomeButton extends HtmlBootstrap {

    public HtmlBootstrapWithHomeButton(String title, Writable... cs) {
        super(title,
                new HtmlElem("div", cs),
                btnGroupJustified(
                        btnGroup(a("/", "Home")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default")
                        )
                ).withAttr("class", "text-left")
        );
    }
}
