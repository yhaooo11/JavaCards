package model;

import java.util.LinkedList;
import java.util.List;

// Represents a deck of flashcards with a name. currentCard is the card the user is to review. cardsReviewed is the
// number of cards reviewed so far.
public class FlashcardDeck {
    private List<Flashcard> deck;
    private String name;
    private int currentCard;
    private int cardsReviewed;

    // REQUIRES: name is not the empty string
    // EFFECTS: constructs a flashcard deck with given name, 0 cards in deck, -1 current card and 0 cards reviewed
    public FlashcardDeck(String name) {
        deck = new LinkedList<>();
        this.name = name;
        currentCard = 0;
        cardsReviewed = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the given flashcard to the end of the deck
    public void addCard(Flashcard card) {
        deck.add(card);
    }

    // REQUIRES: Deck has at least one card and cardNumber >= 0 and < length of deck
    // MODIFIES: this
    // EFFECTS: deletes card in deck with index of cardNumber
    public void deleteCard(int cardIndex) {
        deck.remove(cardIndex);
    }

    // REQUIRES: Deck has at least one card and cardNumber >= 0 and < length of deck
    // MODIFIES: this
    // EFFECTS: removes the given card from the deck
    public void deleteCard(Flashcard card) {
        deck.remove(card);
    }

    // EFFECTS: returns the number of cards in this deck
    public int length() {
        return deck.size();
    }

    // MODIFIES: this
    // EFFECTS: increases current card by 1, if increasing goes over deck length, reset to 0
    public void increaseCurrentCard() {
        int nextCard = currentCard + 1;

        if (nextCard >= deck.size()) {
            currentCard = 0;
        } else {
            currentCard++;
        }
    }

    // MODIFIES: this
    // EFFECTS: decreases current card by 1, if decreasing goes to less than 0, reset to end of deck
    public void decreaseCurrentCard() {
        int nextCard = currentCard - 1;

        if (nextCard < 0) {
            currentCard = deck.size() - 1;
        } else {
            currentCard--;
        }
    }

    // EFFECTS: returns the number of cards reviewed in deck
    public int getCardsReviewed() {
        int amountReviewed = 0;
        for (Flashcard card : deck) {
            if (card.getReviewedStatus()) {
                amountReviewed++;
            }
        }
        return amountReviewed;
    }

    // EFFECTS: returns true if all cards in deck have been reviewed
    public boolean isDeckDone() {
        for (Flashcard card : deck) {
            if (!card.getReviewedStatus()) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: deck that resetDeck() is called on must be done (all cards reviewed)
    // MODIFIES: this
    // EFFECTS: resets deck by setting all cards as not reviewed
    public void resetDeck() {
        for (Flashcard card : deck) {
            card.setAsNotReviewed();
        }
    }

    // MODIFIES: this
    // EFFECTS: increases the amount of cards reviewed by one. If increasing goes over amount of cards in deck,
    // reset to 0
    public void increaseCardsReviewed() {
        cardsReviewed++;

        if (cardsReviewed > deck.size()) {
            cardsReviewed = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets name of deck to given name
    public void setName(String name) {
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getCurrentCardNum() {
        return currentCard;
    }

    public int getNumCardsReviewed() {
        return cardsReviewed;
    }

    // REQUIRES: deck has at least one card and cardIndex >= 0 and < length of deck
    // EFFECT: returns the card in deck at given index
    public Flashcard getCard(int cardIndex) {
        return deck.get(cardIndex);
    }
}
