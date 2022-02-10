package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FlashcardTest {
    private Flashcard testFlashcard;

    @BeforeEach
    void runBefore() {
        testFlashcard = new Flashcard("Front", "Back");
    }

    @Test
    void testConstructor() {
        assertEquals("Front", testFlashcard.getFrontText());
        assertEquals("Back", testFlashcard.getBackText());
    }

    @Test
    void testSetFrontText() {
        testFlashcard.setFrontText("New Front");
        assertEquals("New Front", testFlashcard.getFrontText());
    }

    @Test
    void testSetBackText() {
        testFlashcard.setBackText("New Back");
        assertEquals("New Back", testFlashcard.getBackText());
    }

}
