package pt.isel.ls.movies.engine;

import java.io.*;
import java.util.HashMap;

/**
 * Handles the response of a request
 */
public class Response {

    private String contentType;
    private Writer writer;
    private int status;
    private HashMap<String, String> header;

    private Response(Writer writer) {
        this.writer = writer;
        this.header = new HashMap<>();
    }

    public Writer getWriter() {
        return writer;
    }

    public void write(String content) throws IOException {
        writer.write(content);
        writer.flush();
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
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

    public static Response create(Writer writer) throws IOException {
        return new Response(writer);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getHeader(String key) {
        return header.getOrDefault(key, null);
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }
}
