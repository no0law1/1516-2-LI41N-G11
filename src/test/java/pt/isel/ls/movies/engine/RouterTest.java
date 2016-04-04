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
        assertTrue(router.get("GET", "/movies") instanceof GetMovieList);
    }

    @Test
    public void testGet2() throws Exception {
        assertTrue(router.get("GET", "/tops/ratings/higher/average") instanceof GetTopsRatingHigherAverage);
    }

    @Test
    public void testGet3() throws Exception {
        assertTrue(router.get("POST", "/movies") instanceof PostMovie);
    }

    @Test
    public void testGetWithParameterOnPath() throws Exception {
        assertTrue(router.get("GET", "/movies/12") instanceof GetMovie);
    }

    @Test
    public void testGetWithParameterInTheMiddleOfPath() throws Exception {
        assertTrue(router.get("GET", "/tops/{p}/ratings/higher/average") instanceof GetNTopsRatingHigherAverage);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetNotExistingMethod() throws Exception {
        router.get("SDA", "/movies");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetNotExistingPath() throws Exception {
        router.get("GET", "/movies/test");
    }
}