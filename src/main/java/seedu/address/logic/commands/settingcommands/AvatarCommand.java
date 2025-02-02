package seedu.address.logic.commands.settingcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Class that represents a command to change the avatar of the player to 1 of 151 of the original pokemon.
 */
public class AvatarCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "avatar";

    public static final String MESSAGE_USAGE = "Parameters: avatar [AVATAR_ID]\n"
            + "Example: " + COMMAND_WORD + " 143. "
            + "Set to 0 to change to a random avatar after every command.";

    private final int avatarId;

    public AvatarCommand(int avatarId) {
        this.avatarId = avatarId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setAvatarId(this.avatarId);
        return new CommandResult("Avatar now set to: " + (avatarId == 0 ? "RANDOM" : avatarId));
    }

}
