package pt.isel.ls.movies.engine;

import java.io.UnsupportedEncodingException;

/**
 * Contract of a Request class.
 */
public interface IRequest {

    String getMethod();

    String getPath();

    String getHeader(String key) throws UnsupportedEncodingException;

    String getParameter(String key) throws UnsupportedEncodingException;

}
