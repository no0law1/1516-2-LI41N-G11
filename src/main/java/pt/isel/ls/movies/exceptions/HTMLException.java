package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class HTMLException extends Exception{
    private int htmlErrorCode;

    public HTMLException(int htmlErrorCode, String message){
        super(message);
        this.htmlErrorCode = htmlErrorCode;
    }

    public HTMLException(int htmlErrorCode, String message, Throwable throwable){
        super(message, throwable);
        this.htmlErrorCode = htmlErrorCode;
    }

    public int getHtmlErrorCode() {
        return htmlErrorCode;
    }
}
