package pt.isel.ls.movies.engine;

import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Class to test {@link Request}
 */
public class RequestTest {

    @Test
    public void getQueryParams() throws Exception {
        Map<String, String> expected= new HashMap<>();
        expected.put("name", "Avengers");
        expected.put("rating", "5");

        String[] args = new String[]{"POST", "/movies/123", "name=Avengers&rating=5"};
        Request request = Request.create(new ContextData(), args);

        assertEquals(expected.get("name"), request.getParameter("name"));
        assertEquals(expected.get("rating"), request.getParameter("rating"));
    }

    @Test
    public void getQueryHeaders() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("accept", "text/plain");
        expected.put("accept-language", "en-gb");

        String[] args = new String[]{"GET", "/movies/123", "accept:text/plain|accept-language:en-gb"};
        Request request = Request.create(new ContextData(), args);

        assertEquals(expected.get("accept"), request.getHeader("accept"));
        assertEquals(expected.get("accept-language"), request.getHeader("accept-language"));
    }

    @Test
    public void getQueryHeadersAndParameters() throws Exception {
        String[] args = new String[]{
                "POST",
                "/movies/123",
                "accept:text/plain|accept-language:en-gb",
                "name=Avengers&rating=5"};
        Request request = Request.create(new ContextData(), args);

        assertEquals("text/plain", request.getHeader("accept"));
        assertEquals("en-gb", request.getHeader("accept-language"));

        assertEquals("Avengers", request.getParameter("name"));
        assertEquals("5", request.getParameter("rating"));
    }

    @Test
    public void getQueryHeadersAndParametersAvoidingCase() throws Exception {
        String[] args = new String[]{
                "POST",
                "/movies/123",
                "aCcept:text/plain|accept-languaGe:en-gb",
                "naMe=Avengers&ratiNg=5"};
        Request request = Request.create(new ContextData(), args);

        assertEquals("text/plain", request.getHeader("Accept"));
        assertEquals("en-gb", request.getHeader("Accept-language"));

        assertEquals("Avengers", request.getParameter("namE"));
        assertEquals("5", request.getParameter("raTing"));
    }

    @Test
    public void create() throws Exception {
        String[] args = new String[]{
                "POST",
                "/movies/123",
                "Header1:headerValue1|Header2:headerValue2",
                "Parameter1=parameterValue1&Parameter2=parameterValue2"
        };
        Request request = Request.create(new ContextData(), args);
        assertEquals("post", request.getMethod());
        assertEquals("/movies/123", request.getPath());
        assertEquals("headerValue1", request.getHeader("header1"));
        assertEquals("headerValue2", request.getHeader("header2"));
        assertEquals("parameterValue1", request.getParameter("parameter1"));
        assertEquals("parameterValue2", request.getParameter("parameter2"));
    }
    @Test
    public void create1() throws Exception {
        Request request = Request.create(new ContextData(), new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                if(s.equals("Header1")) return "headerValue1";
                if(s.equals("Header2")) return "headerValue2";
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return new Enumeration<String>() {
                    String[] values = {"Header1", "Header2"};
                    int i =2;

                    @Override
                    public boolean hasMoreElements() {
                        return i>0;
                    }

                    @Override
                    public String nextElement() {
                        if(i>0) return values[--i];
                        return null;
                    }
                };
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return "POST";
            }

            @Override
            public String getPathInfo() {
                return "/movies/123";
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String s, String s1) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                Map<String, String[]> parameters = new HashMap<>();
                parameters.put("Parameter1", new String[]{"parameterValue1", "notuse1"});
                parameters.put("Parameter2", new String[]{"parameterValue2", "notuse2"});
                return parameters;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        });
        assertEquals("post", request.getMethod());
        assertEquals("/movies/123", request.getPath());
        assertEquals("headerValue1", request.getHeader("header1"));
        assertEquals("headerValue2", request.getHeader("header2"));
        assertEquals("parameterValue1", request.getParameter("parameter1"));
        assertEquals("parameterValue2", request.getParameter("parameter2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void badCreate() throws Exception {
        String[] args = new String[]{"GET"};
        Request.create(new ContextData(), args);
    }
}