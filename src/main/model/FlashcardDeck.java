package model;

import java.util.List;

// Represents a deck of flashcards
public class FlashcardDeck {

    // REQUIRES: name is not the empty string
    // EFFECTS: constructs a flashcard deck with given name, 0 cards in deck, and 0 cards reviewed
    public FlashcardDeck(String name) {
        // stub
    }

    // REQUIRES: frontText and backText are not the empty string
    // MODIFIES: this
    // EFFECTS: adds a flashcard to end of the deck with given front and back text
    public void addCard(String frontText, String backText) {
        // stub
    }
    // REQUIRES: cardNumber >= 0 and < length of deck
    // MODIFIES: this
    // EFFECTS: deletes card in deck with index of cardNumber
    public void deleteCard(int cardNumber) {
        // stub
    }

    // getters

    public String getName() {
        return null;
    }

    public int getNumCardsReviewed() {
        return 0;
    }

    public List<Flashcard> getCards() {
         return null;
    }
}
