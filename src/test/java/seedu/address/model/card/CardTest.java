package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEANING_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

//import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CardBuilder;

public class CardTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Card card = new CardBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> card.getTags().remove(0));
    }

    @Test
    public void isSameMeaning() {
        // same object -> returns true
        assertTrue(ABRA.isSameMeaning(ABRA));

        // null -> returns false
        assertFalse(ABRA.isSameMeaning(null));

        // different word -> returns true
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).build();
        assertTrue(ABRA.isSameMeaning(editedAbra));

        // same word, different meanings -> returns false
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_BUG).build();
        assertFalse(ABRA.isSameMeaning(editedAbra));
    }

    //    @Test
    //    public void getHint() {
    //        String wordStr = "Pikachu";
    //        String meaningStr = "PIKA PIKA";
    //        Card card = new Card(new Word(wordStr), new Meaning(meaningStr), new HashSet<>());
    //        for (int i = 0; i < wordStr.length(); ++i) {
    //            Hint hint = card.getHint();
    //            assertTrue(wordStr.charAt(hint.index.getZeroBased()) == hint.letter);
    //        }
    //        assertTrue(card.getHint() == null); // hints exhausted
    //    }



    @Test
    public void equals() {
        // same values -> returns true
        Card abraCopy = new CardBuilder(ABRA).build();
        assertTrue(ABRA.equals(abraCopy));

        // same object -> returns true
        assertTrue(ABRA.equals(ABRA));

        // null -> returns false
        assertFalse(ABRA.equals(null));

        // different type -> returns false
        assertFalse(ABRA.equals(5));

        // different Card -> returns false
        assertFalse(ABRA.equals(BUTTERFREE));

        // different name -> returns false
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different description -> returns false
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different tags -> returns false
        editedAbra = new CardBuilder(ABRA).withTags(VALID_TAG_BUG).build();
        assertFalse(ABRA.equals(editedAbra));
    }
}
