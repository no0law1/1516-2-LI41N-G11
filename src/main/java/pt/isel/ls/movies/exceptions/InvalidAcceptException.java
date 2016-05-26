package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class InvalidAcceptException extends HTMLException {
    public InvalidAcceptException(String message){
        super(406, "Not Acceptable", message);
    }

    public InvalidAcceptException(String message, Throwable throwable){
        super(406, "Not Acceptable", message, throwable);
    }
}
