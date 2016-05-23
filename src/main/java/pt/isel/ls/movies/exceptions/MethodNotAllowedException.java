package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class MethodNotAllowedException extends Exception {
    public MethodNotAllowedException(String message){
        super(message);
    }

    public MethodNotAllowedException(String message, Throwable throwable){
        super(message, throwable);
    }
}
