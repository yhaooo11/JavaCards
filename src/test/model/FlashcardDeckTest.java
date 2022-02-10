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
        assertEquals(0, testDeck.length());
        assertEquals(0, testDeck.getNumCardsReviewed());
        assertEquals(-1, testDeck.getCurrentCardNum());
    }

    @Test
    void testAddCardOnce() {
        Flashcard card = new Flashcard("Front", "Back");
        testDeck.addCard(card);
        assertEquals(1, testDeck.length());
        assertEquals(0, testDeck.getNumCardsReviewed());
        assertEquals(card, testDeck.getCard(0));
    }

    @Test
    void testAddCardTwice() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");

        testDeck.addCard(card1);
        assertEquals(1, testDeck.length());
        assertEquals(card1, testDeck.getCard(0));

        testDeck.addCard(card2);
        assertEquals(2, testDeck.length());
        assertEquals(card2, testDeck.getCard(1));
    }

    @Test
    void testDeleteCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        testDeck.deleteCard(0);
        assertEquals(2, testDeck.length());
        assertEquals(card2, testDeck.getCard(0));
        assertEquals(card3, testDeck.getCard(1));

        testDeck.deleteCard(1);
        assertEquals(1, testDeck.length());
        assertEquals(card2, testDeck.getCard(0));
    }

    @Test
    void testGetNextCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        assertEquals(card1, testDeck.getNextCard());
        assertEquals(card2, testDeck.getNextCard());
        assertEquals(card3, testDeck.getNextCard());
    }

    @Test
    void testGetNextCardLast() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        assertEquals(card1, testDeck.getNextCard());
        assertEquals(card2, testDeck.getNextCard());
        assertEquals(card1, testDeck.getNextCard());
    }

    @Test
    void testIncreaseCardsReviewed() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);


        testDeck.increaseCardsReviewed();
        assertEquals(1, testDeck.getNumCardsReviewed());

        testDeck.increaseCardsReviewed();
        assertEquals(2, testDeck.getNumCardsReviewed());
    }

    @Test
    void testIncreaseCardsReviewedReset() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);


        testDeck.increaseCardsReviewed();
        assertEquals(1, testDeck.getNumCardsReviewed());

        testDeck.increaseCardsReviewed();
        assertEquals(2, testDeck.getNumCardsReviewed());

        testDeck.increaseCardsReviewed();
        assertEquals(0, testDeck.getNumCardsReviewed());
    }

}