package pt.isel.ls.movies.view.utils;

/**
 * Utility methods to aid in HTML
 */
public class HtmlUtils {

    private static final String charset = "UTF-8";

    public static String makeHeader(String name) {
        return new StringBuilder()
                .append("<head>\n")
                .append("\t<meta charset=\"" + charset + "\">\n")
                .append("\t<meta name=\"viewport\" content=\"width=device-width\">\n")
                .append("\t<title>" + name + "</title>\n")
                .append("</head>")
                .toString();
    }

}
