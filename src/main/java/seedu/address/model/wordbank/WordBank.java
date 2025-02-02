package seedu.address.model.wordbank;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;

/**
 * Word bank stores multiple word - meaning pair
 * Duplicates of cards are not allowed (by Card#isSameName(Card) comparison)
 */
public class WordBank implements ReadOnlyWordBank {
    private final UniqueCardList cards;
    private String name;

    /**
     * Creates a word bank with the unique name.
     *
     * @param name of the word bank.
     */
    public WordBank(String name) {
        this.name = name;
        cards = new UniqueCardList();
    }

    /**
     * Resets the existing data of this {@code WordBank} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBank newData) {
        requireNonNull(newData);
        setCards(newData.getCardList());
    }

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain any cards with the same meaning.
     */
    public void setCards(List<Card> cards) {
        requireNonNull(cards);
        this.cards.setCards(cards);
    }

    /**
     * Returns true if a card with the same meaning as {@code card} exists in the word bank.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     * The checking is handled by UniqueCardList class.
     */
    public void addCard(Card p) {
        cards.add(p);
    }

    /**
     * Replaces the given card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the word bank.
     * The card meaning of {@code editedCard} must not be the same as another existing card in the word bank.
     */
    public void setCard(Card target, Card editedCard) {
        requireNonNull(editedCard);
        cards.setCard(target, editedCard);
    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    /**
     * Returns a clone of the {@code card} at the specified {@index}.
     */
    @Override
    public Card getCard(Index index) {
        return cards.get(index).clone();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && cards.equals(((WordBank) other).cards))
                && name.equals(((WordBank) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns true if both cards have the same meaning.
     */
    public boolean isSameName(WordBank other) {
        if (other == null) {
            return false;
        }
        return getName().equals(other.getName());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
