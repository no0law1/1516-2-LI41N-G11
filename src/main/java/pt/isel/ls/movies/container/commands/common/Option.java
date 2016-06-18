package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;

/**
 * Lists all the commands available and their description
 */
public class Option extends Command {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new Option(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("OPTION", "/", null, "Lists all the available options");
        }
    };

    public Option(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        for (ICommand c : request.getContextData().router.getCommands()) {
            Creator creator = (Creator) c.getClass().getField("CREATOR").get(null);
            CommandDetails details = creator.details();
            System.out.printf("Method: %s Path: %s Parameters: %s\n\t%s\n",
                    details.method,
                    details.path,
                    details.parameters == null ? "<No Parameters>" : details.parameters,
                    details.details);
        }
    }

}
