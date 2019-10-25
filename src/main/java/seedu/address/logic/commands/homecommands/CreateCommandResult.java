package seedu.address.logic.commands.homecommands;

import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code CreateCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class CreateCommandResult extends HomeCommandResult {

    public CreateCommandResult(String feedback, String name) {
        super(feedback, name);
    }

    @Override
    public void updateStorage(Storage storage) {
        storage.saveWordBank(name);
    }
}
