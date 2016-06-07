package pt.isel.ls.movies.exceptions;

import pt.isel.ls.movies.exceptions.common.HTMLException;

/**
 * Exception that represents a Bad Request in http
 */
public class BadRequestException extends HTMLException {

    public BadRequestException(String message) {
        super(400, "Bad Request", message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(400, "Bad Request", message, throwable);
    }
}
