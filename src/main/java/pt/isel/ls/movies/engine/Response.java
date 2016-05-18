package pt.isel.ls.movies.engine;

import java.io.*;

/**
 * Handles the response of a request
 */
public class Response {

    private String content;
    private String contentType;
    private Writer writer;

    private Response(Writer writer) {
        this.writer = writer;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void write() throws IOException {
        writer.write(content);
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
