package model;

import java.util.LinkedList;
import java.util.List;

/*
Represents a deck of flashcards. A deck has a name and the index of the current card the user is to review.
 */

public class FlashcardDeck {
    private List<Flashcard> deck;
    private String name;
    private int currentCard;

    // REQUIRES: name is not the empty string
    // EFFECTS: constructs a flashcard deck with given name, an empty deck, and 0 current card
    public FlashcardDeck(String name) {
        deck = new LinkedList<>();
        this.name = name;
        currentCard = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the given flashcard to the end of the deck
    public void addCard(Flashcard card) {
        deck.add(card);
    }

    // REQUIRES: Deck has at least one card and cardIndex >= 0 and < length of deck
    // MODIFIES: this
    // EFFECTS: deletes card in deck at the given index
    public void deleteCard(int cardIndex) {
        deck.remove(cardIndex);
    }

    // REQUIRES: Deck has the given card in it
    // MODIFIES: this
    // EFFECTS: removes the given card from the deck
    public void deleteCard(Flashcard card) {
        deck.remove(card);
    }

    // EFFECTS: returns the number of cards in this deck
    public int length() {
        return deck.size();
    }

    // REQUIRES: deck is not empty
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

    // REQUIRES: deck is not empty
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

    // EFFECTS: returns the number of cards that have been reviewed in deck
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
    // MODIFIES: card
    // EFFECTS: resets deck by setting all cards as not reviewed
    public void resetDeck() {
        for (Flashcard card : deck) {
            card.setAsNotReviewed();
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

    // REQUIRES: deck has at least one card and cardIndex >= 0 and < length of deck
    // EFFECT: returns the card in deck at given index
    public Flashcard getCard(int cardIndex) {
        return deck.get(cardIndex);
    }
}
