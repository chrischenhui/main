package seedu.address.logic.commands.load;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.model.Model;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * Terminates the program.
 */
public class BankCommand extends LoadCommand {

    public static final String COMMAND_WORD = "bank";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the bank identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static String name;
    private final Index targetIndex;

    public BankCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

//    public BankCommand(String name) {
//        this.name = name;
//    }

    @Override
    public CommandResult execute(Model model) {
        WordBankList temp = new WordBankList();
        // Get word bank from storage
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        String wordBankName = temp.getWordBank(targetIndex).getName();
        System.out.println("+-+-+-" + temp.getWordBank(targetIndex).getName());
        model.setWordBank(temp.getWordBank(targetIndex));
        return new BankCommandResult(FileUtil.getJustFileName(wordBankName));
    }

}
