package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

/*
Represents a deck of flashcards. A deck has a name and the index of the current card the user is to review.
 */

public class FlashcardDeck implements Writable {
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

        if (cardIndex == length()) {
            currentCard = 0;
        }
    }

    // REQUIRES: Deck has the given card in it
    // MODIFIES: this
    // EFFECTS: removes the given card from the deck, if removing last card, set currentCard to 0
    public void deleteCard(Flashcard card) {
        int index = deck.indexOf(card);
        deck.remove(card);

        if (index == length()) {
            currentCard = 0;
        }
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

    // MODIFIES: deck
    // REQUIRES: deck has at least one card in it
    // EFFECTS: checks if all cards have reviewed and resets it if all reviewed and increases current card by 1,
    // then returns new current card
    public Flashcard getNextCard() {
        if (isDeckDone()) {
            resetDeck();
        }
        increaseCurrentCard();
        int cardNum = getCurrentCardNum();
        return getCard(cardNum);
    }

    // MODIFIES: deck
    // REQUIRES: deck has at least one card in it
    // EFFECTS: checks if all cards have reviewed and resets it if all reviewed and decreases current card by 1,
    // then returns new current card
    public Flashcard getPreviousCard() {
        if (isDeckDone()) {
            resetDeck();
        }
        decreaseCurrentCard();
        int cardNum = getCurrentCardNum();
        return getCard(cardNum);
    }

    // MODIFIES: this
    // EFFECTS: sets name of deck to given name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // REQUIRES: num >= 0
    // EFFECTS: sets currentCard to the given integer
    public void setCurrentCard(int num) {
        currentCard = num;
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

    @Override
    // EFFECTS: returns this deck as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("currentCard", this.currentCard);
        json.put("cards", cardsToJson());
        return json;
    }

    // EFFECTS: returns cards in this deck as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Flashcard card : deck) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }
}
