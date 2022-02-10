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
        currentCard = -1;
        cardsReviewed = 0;
    }

    // REQUIRES: nothing
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

    // REQUIRES: Deck has at least one card in it
    // EFFECTS: gets next card in deck to review and increments currentCard by one. If at last card, getNextCard will
    // return the first card in the deck.
    public Flashcard getNextCard() {
        currentCard++;

        if (currentCard == deck.size()) {
            currentCard = 0;
            return deck.get(0);
        }

        return deck.get(currentCard);

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

    public Flashcard getCard(int cardIndex) {
        return deck.get(cardIndex);
    }

    // EFFECTS: returns the number of cards in this deck
    public int length() {
        return deck.size();
    }
}
