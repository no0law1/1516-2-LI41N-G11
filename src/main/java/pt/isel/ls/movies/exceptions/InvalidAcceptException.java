package pt.isel.ls.movies.exceptions;

import pt.isel.ls.movies.exceptions.common.HTMLException;

public class InvalidAcceptException extends HTMLException {
    public InvalidAcceptException(String message){
        super(406, "Not Acceptable", message);
    }

    public InvalidAcceptException(String message, Throwable throwable){
        super(406, "Not Acceptable", message, throwable);
    }
}
