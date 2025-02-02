package seedu.address.logic.commands.homecommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends HomeCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting App as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
