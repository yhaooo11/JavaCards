package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardDeckTest {
    private FlashcardDeck testDeck;

    @BeforeEach
    void runBefore() {
        testDeck = new FlashcardDeck("Test Deck");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Deck", testDeck.getName());
        assertEquals(0, testDeck.getCards().size());
        assertEquals(0, testDeck.getNumCardsReviewed());
    }

}