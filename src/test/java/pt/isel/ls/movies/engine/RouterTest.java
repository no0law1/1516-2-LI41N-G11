package pt.isel.ls.movies.engine;

import org.junit.Test;
import pt.isel.ls.movies.container.commands.common.Exit;
import pt.isel.ls.movies.container.commands.common.Option;
import pt.isel.ls.movies.container.commands.movies.GetMovie;
import pt.isel.ls.movies.container.commands.movies.GetMovieList;
import pt.isel.ls.movies.container.commands.movies.PostMovie;
import pt.isel.ls.movies.container.commands.movies.tops.GetHighestRatedMovie;
import pt.isel.ls.movies.container.commands.movies.tops.GetHighestRatedMovies;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.exceptions.MethodNotAllowedException;
import pt.isel.ls.movies.exceptions.PathNotFoundException;

import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Class to test {@link Router}
 */
public class RouterTest {

    Router router;

    public RouterTest(){
        router = new Router();
        createRouter(DataSourceFactory.createTestInstance());
    }

    public final void createRouter(DataSource ds){
        router.add("POST", "/movies", new PostMovie(ds));
        router.add("GET", "/movies", new GetMovieList(ds));
        router.add("GET", "/movies/{mid}", new GetMovie(ds));
        router.add("GET", "/tops/ratings/higher/average", new GetHighestRatedMovie(ds));
        router.add("GET", "/tops/{n}/ratings/higher/average", new GetHighestRatedMovies(ds));
        router.add("EXIT", "/", new Exit(ds));
        router.add("OPTION", "/", new Option(ds));
    }

    @Test
    public void testGet() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies/12"});
        assertTrue(router.get(request) instanceof GetMovie);
    }

    @Test
    public void testGet2() throws Exception {
        Request request = Request.create(new String[]{"GET", "/tops/ratings/higher/average"});
        assertTrue(router.get(request) instanceof GetHighestRatedMovie);
    }

    @Test
    public void testGet3() throws Exception {
        Request request = Request.create(new String[]{"POST", "/movies"});
        assertTrue(router.get(request) instanceof PostMovie);
    }

    @Test
    public void testGetWithParameterOnPath() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies/12"});
        assertTrue(router.get(request) instanceof GetMovie);
    }

    @Test
    public void testGetWithParameterInTheMiddleOfPath() throws Exception {
        Request request = Request.create(new String[]{"GET", "/tops/{n}/ratings/higher/average"});
        assertTrue(router.get(request) instanceof GetHighestRatedMovies);
    }

    @Test(expected = MethodNotAllowedException.class)
    public void testGetNotExistingMethod() throws Exception {
        Request request = Request.create(new String[]{"SDA", "/movies"});
        router.get(request);
    }

    @Test(expected = PathNotFoundException.class)
    public void testGetNotExistingPath() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies/test/unholy"});
        router.get(request);
    }

    @Test
    public void testGetPathParameter() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies/12"});
        router.get(request);

        assertEquals("12", request.getParameter("mid"));
    }

    @Test
    public void testGetCommonCommand() throws Exception {
        Request request = Request.create(new String[]{"EXIT", "/"});
        assertTrue(router.get(request) instanceof Exit);
    }

    @Test
    public void testGetCommonCommandOption() throws Exception {
        Request request = Request.create(new String[]{"OPTION", "/"});
        assertTrue(router.get(request) instanceof Option);
    }

    @Test
    public void testCreateRouter() throws Exception {
        Router router = Router.createRouter(DataSourceFactory.createTestInstance());
        Request request1 = Request.create(new String[]{"OPTION", "/"});
        Request request2 = Request.create(new String[]{"GET", "/movies/12"});
        Request request3 = Request.create(new String[]{"GET", "/tops/{n}/ratings/higher/average"});

        assertNotNull(router);
        assertTrue(router.get(request1) instanceof Option);
        assertTrue(router.get(request2) instanceof GetMovie);
        assertTrue(router.get(request3) instanceof GetHighestRatedMovies);
    }
}