package pt.isel.ls.movies.exceptions.common;

/**
 * Created by rcacheira on 23/05/16.
 */
public class HTMLException extends Exception{
    private int htmlErrorCode;
    private String errorTitle;

    public HTMLException(int htmlErrorCode, String errorTitle, String message){
        super(message);
        this.htmlErrorCode = htmlErrorCode;
        this.errorTitle = errorTitle;
    }

    public HTMLException(int htmlErrorCode, String errorTitle, String message, Throwable throwable){
        super(message, throwable);
        this.htmlErrorCode = htmlErrorCode;
        this.errorTitle = errorTitle;
    }

    public int getHtmlErrorCode() {
        return htmlErrorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }
}
