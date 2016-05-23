package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class MethodNotAllowedException extends HTMLException {
    public MethodNotAllowedException(String message){
        super(405, message);
    }

    public MethodNotAllowedException(String message, Throwable throwable){
        super(405, message, throwable);
    }
}
