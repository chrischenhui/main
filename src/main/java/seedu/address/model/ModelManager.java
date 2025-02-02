package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.appsettings.ReadOnlyAppSettings;
import seedu.address.model.appsettings.ThemeEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.game.Game;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private boolean hasBank = false;

    private WordBank currentWordBank = SampleDataUtil.getSampleWordBank();
    private final WordBankList wordBankList;

    private WordBankStatistics wordBankStatistics;
    private final WordBankStatisticsList wordBankStatisticsList;

    private final GlobalStatistics globalStatistics;

    private final UserPrefs userPrefs;

    private final AppSettings appSettings;

    private FilteredList<Card> filteredCards;

    private Game game = null;

    /**
     * Initializes a ModelManager with the given wordBank and userPrefs.
     */
    public ModelManager(WordBankList wordBankList, WordBankStatisticsList wbStatsList,
                        GlobalStatistics globalStatistics, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyAppSettings appSettings) {
        super();
        requireAllNonNull(wordBankList, userPrefs);

        logger.fine("Initializing with word bank list: " + wordBankList + " and user prefs " + userPrefs);

        this.wordBankList = wordBankList;
        this.wordBankStatisticsList = wbStatsList;
        this.globalStatistics = globalStatistics;

        this.userPrefs = new UserPrefs(userPrefs);
        this.appSettings = new AppSettings(appSettings);

        filteredCards = new FilteredList<>(this.currentWordBank.getCardList());
    }

    public ModelManager() {
        this(new WordBankList(Collections.emptyList()),
                new WordBankStatisticsList(Collections.emptyList()),
                new GlobalStatistics(),
                new UserPrefs(),
                new AppSettings());
    }

    // Placeholder setGame method
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean gameIsOver() {
        return this.game == null ? true : game.isOver();
    }

    @Override
    public DifficultyEnum getCurrentGameDifficulty() {
        return game.getCurrentGameDifficulty();
    }

    //=========== AppSettings ================================================================================
    @Override
    public AppSettings getAppSettings() {
        return appSettings;
    }

    @Override
    public Path getAppSettingsFilePath() {
        return appSettings.getAppSettingsFilePath();
    }

    @Override
    public void setDefaultDifficulty(DifficultyEnum difficultyEnum) {
        appSettings.setDefaultDifficulty(difficultyEnum);
    }

    @Override
    public DifficultyEnum getDefaultDifficulty() {
        return appSettings.getDefaultDifficulty();
    }

    @Override
    public ThemeEnum getDefaultTheme() {
        return appSettings.getDefaultTheme();
    }

    @Override
    public void setDefaultTheme(ThemeEnum defaultTheme) {
        appSettings.setDefaultTheme(defaultTheme);
    }

    @Override
    public void setHintsEnabled(boolean enabled) {
        requireNonNull(enabled);
        appSettings.setHintsEnabled(enabled);
    }

    @Override
    public boolean getHintsEnabled() {
        return appSettings.getHintsEnabled();
    }

    @Override
    public void setAvatarId(int avatarId) {
        requireNonNull(avatarId);
        appSettings.setAvatarId(avatarId);
    }

    @Override
    public int getAvatarId() {
        return appSettings.getAvatarId();
    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return this.game.getCurrentGameDifficulty().getTimeAllowedPerQuestion();
    }

    @Override
    public FormattedHint getHintFormatFromCurrentGame() throws UnsupportedOperationException {
        if (game == null || game.isOver()) {
            throw new UnsupportedOperationException("No active game session to send hints from");
        }
        return game.getHintFormatForCurrCard();
    }

    @Override
    public int getHintFormatSizeFromCurrentGame() {
        return game.getHintFormatSizeOfCurrCard();
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getWordBankFilePath() {
        return userPrefs.getDataFilePath();
    }

    @Override
    public void setWordBankFilePath(Path filePath) {
        requireNonNull(filePath);
        userPrefs.setDataFilePath(filePath);
    }

    //=========== WordBank ================================================================================

    @Override
    public void setCurrentWordBank(ReadOnlyWordBank currenWordBank) {
        this.currentWordBank = (WordBank) currenWordBank;
        hasBank = true;
        filteredCards = new FilteredList<>(this.currentWordBank.getCardList());
        //        this.wordBank.resetData(wordBank);
    }

    /**
     * Clears the WordBank.
     */
    public void clearWordBank() {
        currentWordBank.resetData(new WordBank(currentWordBank.getName()));
        filteredCards = new FilteredList<>(this.currentWordBank.getCardList());
        //        this.wordBank.resetData(wordBank);
    }

    @Override
    public ReadOnlyWordBank getCurrentWordBank() {
        return currentWordBank;
    }

    @Override
    public void updateWordBank(String name) {
        if (currentWordBank.getName().equals(name)) {
            hasBank = false;
            this.currentWordBank = SampleDataUtil.getSampleWordBank();
        }
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return currentWordBank.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        currentWordBank.removeCard(target);
        wordBankStatistics.removeCardStatistics(target.getId());
    }

    @Override
    public void addCard(Card card) {
        currentWordBank.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);
        currentWordBank.setCard(target, editedCard);
    }

    //=========== WordBankList ============================================================================

    @Override
    public boolean hasWordBank(String name) {
        return wordBankList.hasWordBankName(name);
    }

    @Override
    public WordBank getWordBankFromName(String name) {
        return wordBankList.getWordBankFromName(name);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        filteredCards = new FilteredList<>(this.currentWordBank.getCardList());
        return filteredCards;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return wordBankList.getFilteredWordBankList();
    }

    @Override
    public WordBankList getWordBankList() {
        return wordBankList;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);

        filteredCards.setPredicate(predicate);
        //filteredCards = new FilteredList<>(this.wordBank.getCardList());
    }

    //=========== WordBankStatistics methods =============================================================

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return wordBankStatisticsList;
    }

    @Override
    public WordBankStatistics getWordBankStatistics() {
        return this.wordBankStatistics;
    }

    @Override
    public boolean getHasBank() {
        return hasBank;
    }

    @Override
    public void setWordBankStatistics(WordBankStatistics wordBankStats) {
        this.wordBankStatistics = wordBankStats;
    }

    @Override
    public void clearWordBankStatistics() {
        this.wordBankStatistics = null;
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return currentWordBank.equals(other.currentWordBank)
                && userPrefs.equals(other.userPrefs)
                && filteredCards.equals(other.filteredCards);
    }
}
