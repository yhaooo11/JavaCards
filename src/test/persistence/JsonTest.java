package persistence;

import model.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCard(String front, String back, Boolean reviewedStatus, Flashcard card) {
        assertEquals(front, card.getFrontText());
        assertEquals(back, card.getBackText());
        assertEquals(reviewedStatus, card.getReviewedStatus());
    }
}
