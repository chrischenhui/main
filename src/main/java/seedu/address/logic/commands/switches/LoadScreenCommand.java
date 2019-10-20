package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class LoadScreenCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going load page as requested";

    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.LOAD;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
