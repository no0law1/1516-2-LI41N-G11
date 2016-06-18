package pt.isel.ls.movies.container.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.movies.container.commands.collections.CreateCollection;
import pt.isel.ls.movies.container.commands.collections.GetCollections;
import pt.isel.ls.movies.container.commands.movies.PostMovie;
import pt.isel.ls.movies.container.commands.rating.PostRating;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.exceptions.BadParameterFormatException;
import pt.isel.ls.movies.exceptions.BadRequestException;
import pt.isel.ls.movies.exceptions.InvalidAcceptException;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Commentary.
 */
public class CommandTest {

    private DataSource dataSource;

    private GetCollections getCollections;

    private CreateCollection createCollection;

    private PostMovie postMovie;

    private PostRating postRating;

    @Before
    public void setUp() {
        dataSource = DataSourceFactory.createTestInstance();
        getCollections = new GetCollections(dataSource);
        createCollection = new CreateCollection(dataSource);
        postMovie = new PostMovie(dataSource);
        postRating = new PostRating(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("delete from rating").execute();
            connection.prepareStatement("delete from movie_collection").execute();
            connection.prepareStatement("delete from collection").execute();
            connection.prepareStatement("delete from movie").execute();
            connection.prepareStatement("ALTER SEQUENCE collection_id_seq RESTART WITH 1").execute();
            connection.prepareStatement("ALTER SEQUENCE movie_id_seq RESTART WITH 1").execute();
        }
    }

    @Test
    public void testGetCommand() throws Exception {
        Request request = Request.create(null, new String[]{"GET", "/collections"});
        Response response = Response.create((String) null);

        getCollections.execute(request, response);
    }

    @Test(expected = InvalidAcceptException.class)
    public void testBadGetCommandWithBadAccept() throws Exception {
        Request request = Request.create(null, new String[]{"GET", "/collections", "accept:text/application"});
        Response response = Response.create((String) null);

        getCollections.execute(request, response);
    }

    @Test
    public void testRedirectOnCreateCollectionCommand() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/collections", "name=test&description=test"});

        String redirect = createCollection.doWork(request);

        assertEquals(redirect, "/collections/1");
    }

    @Test(expected = BadRequestException.class)
    public void testBadRequestOnCreateCollectionCommandDescription() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/collections", "description=test"});
        Response response = Response.create((String) null);

        createCollection.execute(request, response);
    }

    @Test(expected = BadRequestException.class)
    public void testBadRequestOnCreateCollectionCommandName() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/collections", "name=test"});
        Response response = Response.create((String) null);

        createCollection.execute(request, response);
    }

    @Test
    public void testRedirectOnPostMovieCommand() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/movies", "title=test&releaseYear=2016"});

        String redirect = postMovie.doWork(request);

        assertEquals(redirect, "/movies/1");
    }

    @Test(expected = BadParameterFormatException.class)
    public void testBadRequestOnPostMovieCommandTitle() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/movies", "title=test"});
        Response response = Response.create((String) null);

        postMovie.execute(request, response);
    }

    @Test(expected = BadRequestException.class)
    public void testBadRequestOnPostMovieCommandReleaseYear() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/movies", "releaseYear=2016"});
        Response response = Response.create((String) null);

        postMovie.execute(request, response);
    }

    @Test
    public void testRedirectOnPostRatingCommand() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/movies", "title=test&releaseYear=2016"});
        postMovie.doWork(request);

        request = Request.create(null, new String[]{"POST", "/movies/1/ratings", "rating=5"});
        request.getParametersMap().put("mid", "1");

        String redirect = postRating.doWork(request);

        assertEquals(redirect, "/movies/1/ratings");
    }

    @Test(expected = BadParameterFormatException.class)
    public void testBadRequestOnPostRatingCommandReleaseYear() throws Exception {
        Request request = Request.create(null, new String[]{"POST", "/movies/1/ratings"});
        Response response = Response.create((String) null);

        postMovie.execute(request, response);
    }

}