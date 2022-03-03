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
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testAddCardOnce() {
        Flashcard card = new Flashcard("Front", "Back");
        testDeck.addCard(card);
        assertEquals(1, testDeck.length());
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
    void testDeleteCardIndex() {
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
        assertEquals(0, testDeck.getCurrentCardNum());

        testDeck.deleteCard(1);
        assertEquals(1, testDeck.length());
        assertEquals(card2, testDeck.getCard(0));
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testDeleteCardObject() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        testDeck.deleteCard(card1);
        assertEquals(2, testDeck.length());
        assertEquals(card2, testDeck.getCard(0));
        assertEquals(card3, testDeck.getCard(1));
        assertEquals(0, testDeck.getCurrentCardNum());

        testDeck.deleteCard(card3);
        assertEquals(1, testDeck.length());
        assertEquals(card2, testDeck.getCard(0));
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testDeleteCardIndexLastCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        testDeck.deleteCard(1);
        assertEquals(1, testDeck.length());
        assertEquals(card1, testDeck.getCard(0));
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testDeleteCardObjectLastCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        testDeck.deleteCard(card2);
        assertEquals(1, testDeck.length());
        assertEquals(card1, testDeck.getCard(0));
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testGetNextCardNoReset() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        Flashcard currentCard = testDeck.getNextCard();
        assertEquals(card2, currentCard);
        assertEquals(1, testDeck.getCurrentCardNum());

        currentCard = testDeck.getNextCard();
        assertEquals(card3, currentCard);
        assertEquals(2, testDeck.getCurrentCardNum());

        currentCard = testDeck.getNextCard();
        assertEquals(card1, currentCard);
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testGetNextCardReset() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        card1.setAsReviewed();
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        card3.setAsReviewed();
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        Flashcard currentCard = testDeck.getNextCard();
        assertEquals(card2, currentCard);
        assertEquals(1, testDeck.getCurrentCardNum());

        assertFalse(card1.getReviewedStatus());
        assertFalse(card2.getReviewedStatus());
        assertFalse(card3.getReviewedStatus());
    }

    @Test
    void testGetPreviousCardNoReset() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        Flashcard currentCard = testDeck.getPreviousCard();
        assertEquals(card3, currentCard);
        assertEquals(2, testDeck.getCurrentCardNum());

        currentCard = testDeck.getPreviousCard();
        assertEquals(card2, currentCard);
        assertEquals(1, testDeck.getCurrentCardNum());

        currentCard = testDeck.getPreviousCard();
        assertEquals(card1, currentCard);
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testGetPreviousCardReset() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        card1.setAsReviewed();
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        card3.setAsReviewed();
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        Flashcard currentCard = testDeck.getPreviousCard();
        assertEquals(card3, currentCard);
        assertEquals(2, testDeck.getCurrentCardNum());

        assertFalse(card1.getReviewedStatus());
        assertFalse(card2.getReviewedStatus());
        assertFalse(card3.getReviewedStatus());
    }

    @Test
    void testSetName() {
        testDeck.setName("New Deck Name");
        assertEquals("New Deck Name", testDeck.getName());
    }

    @Test
    void testIncreaseCurrentCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        testDeck.increaseCurrentCard();
        assertEquals(1, testDeck.getCurrentCardNum());

        testDeck.increaseCurrentCard();
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testDecreaseCurrentCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        testDeck.decreaseCurrentCard();
        assertEquals(1, testDeck.getCurrentCardNum());

        testDeck.decreaseCurrentCard();
        assertEquals(0, testDeck.getCurrentCardNum());
    }

    @Test
    void testGetCardsReviewedNone() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        assertEquals(0, testDeck.getCardsReviewed());
    }

    @Test
    void testGetCardsReviewedOne() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        assertEquals(1, testDeck.getCardsReviewed());
    }

    @Test
    void testGetCardsReviewedAll() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        card1.setAsReviewed();
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        Flashcard card3 = new Flashcard("card 3 front", "card 3 back");
        card3.setAsReviewed();
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);

        assertEquals(3, testDeck.getCardsReviewed());
    }

    @Test
    void testIsDeckDoneNotDone() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        assertFalse(testDeck.isDeckDone());
    }

    @Test
    void testIsDeckDoneDone() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        card1.setAsReviewed();
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        assertTrue(testDeck.isDeckDone());
    }

    @Test
    void testResetDeck() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        card1.setAsReviewed();
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        card2.setAsReviewed();
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        testDeck.resetDeck();
        assertFalse(card1.getReviewedStatus());
        assertFalse(card2.getReviewedStatus());
    }

    @Test
    void testSetCurrentCard() {
        Flashcard card1 = new Flashcard("card 1 front", "card 1 back");
        Flashcard card2 = new Flashcard("card 2 front", "card 2 back");
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.setCurrentCard(1);
        assertEquals(1, testDeck.getCurrentCardNum());

        testDeck.setCurrentCard(0);
        assertEquals(0, testDeck.getCurrentCardNum());
    }
}