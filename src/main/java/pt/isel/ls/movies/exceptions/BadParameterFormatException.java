package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class BadParameterFormatException extends PathNotFoundException {
    public BadParameterFormatException(String message){
        super(message);
    }

    public BadParameterFormatException(String message, Throwable throwable){
        super(message, throwable);
    }
}
