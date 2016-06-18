package pt.isel.ls.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Commentary.
 */
public class HerokuLaunch {
    private static Logger logger = LoggerFactory.getLogger(HerokuLaunch.class);

    public static void main(String[] args) {
        try {
            MoviesApp.main(new String[]{"LISTEN", "/", "port=" + System.getenv("PORT")});
        } catch (Exception e) {
            logger.error("main: ", e);
        }
    }
}
