package pt.isel.ls.movies.engine;

import java.io.*;

/**
 * Handles the response of a request
 */
public class Response {

    private Writer writer;

    private Response(Writer writer) {
        this.writer = writer;
    }

    public void write(String response) throws IOException {
        writer.write(response);
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

    public static Response create(String file) throws IOException {
        return new Response(file == null ? new PrintWriter(System.out) : new FileWriter(new File(file)));
    }

    public static Response create(File file) throws IOException {
        return new Response(new FileWriter(file));
    }
}
