package pt.isel.ls.movies.engine;

import org.junit.Test;
import pt.isel.ls.movies.container.commands.Movies.GetMovie;
import pt.isel.ls.movies.container.commands.Movies.GetMovieList;
import pt.isel.ls.movies.container.commands.Movies.PostMovie;
import pt.isel.ls.movies.container.commands.Tops.GetNTopsRatingHigherAverage;
import pt.isel.ls.movies.container.commands.Tops.GetTopsRatingHigherAverage;

import static org.junit.Assert.assertTrue;

/**
 * TODO: comment
 */
public class RouterTest {

    Router router;

    public RouterTest(){
        router = Router.createRouter();
    }

    @Test
    public void testGet() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies"});
        assertTrue(router.get(request) instanceof GetMovieList);
    }

    @Test
    public void testGet2() throws Exception {
        Request request = Request.create(new String[]{"GET", "/tops/ratings/higher/average"});
        assertTrue(router.get(request) instanceof GetTopsRatingHigherAverage);
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
        assertTrue(router.get(request) instanceof GetNTopsRatingHigherAverage);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetNotExistingMethod() throws Exception {
        Request request = Request.create(new String[]{"SDA", "/movies"});
        router.get(request);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetNotExistingPath() throws Exception {
        Request request = Request.create(new String[]{"GET", "/movies/test/unholy"});
        router.get(request);
    }
}