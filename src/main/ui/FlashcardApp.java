package ui;

import model.Flashcard;
import model.FlashcardDeck;

import java.util.LinkedList;
import java.util.Scanner;

/*
Represents the console-based flashcard app. Allows user to add, delete, edit, and review decks
of flashcards.
 */

public class FlashcardApp {
    private LinkedList<FlashcardDeck> decks;
    private Scanner input;

    // EFFECTS: runs the flashcard application
    public FlashcardApp() {
        runFlashcard();
    }

    // source: TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFlashcard() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMenuCommands(command);
            }
        }

        System.out.println("\nGoodbye! ... please come back soon!");
    }

    // source: TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: initializes user's decks and scanner
    private void init() {
        decks = new LinkedList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        if (decks.size() == 0) {
            System.out.println("\nYou currently have no decks.");
            System.out.println("\nSelect from:");
            System.out.println("\ta -> add a deck");
            System.out.println("\tq -> quit");
        } else {
            System.out.println("\nYour current decks are:");
            displayDecks();
            System.out.println("\nSelect from:");
            System.out.println("\tr -> review a deck");
            System.out.println("\ta -> add a deck");
            System.out.println("\td -> delete a deck");
            System.out.println("\te -> edit a deck");
            System.out.println("\tq -> quit");
        }
    }

    // EFFECTS: displays all decks user currently has made in format: #. name (cards reviewed)
    private void displayDecks() {
        for (int i = 0; i < decks.size(); i++) {
            int deckNum = i + 1;
            System.out.println(deckNum + ". " + decks.get(i).getName()
                    + " (" + decks.get(i).getCardsReviewed() + " out of " + decks.get(i).length() + " reviewed)");
        }
    }

    // EFFECTS: processes user command
    private void processMenuCommands(String command) {
        if (command.equals("r")) {
            reviewDeck();
        } else if (command.equals("a")) {
            addDeck();
        } else if (command.equals("d")) {
            if (decks.size() == 0) {
                System.out.println("No decks to delete!");
            } else {
                deleteDeck();
            }
        } else if (command.equals("e")) {
            editDeck();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: allows user to review a deck
    private void reviewDeck() {
        System.out.println("Choose a deck to review (enter the number):");
        displayDecks();
        String command = input.next();

        int deckNum = Integer.parseInt(command);
        if (!checkDeckNum(deckNum)) {
            return;
        }

        FlashcardDeck deck = decks.get(deckNum - 1);
        int cardNum = deck.getCurrentCardNum();
        Flashcard card = deck.getCard(cardNum);

        System.out.println("Front:\n" + card.getFrontText());

        goThroughCards(card, deck);

    }

    // EFFECTS: cycles through the cards in a deck with commands on what to do
    private void goThroughCards(Flashcard card, FlashcardDeck deck) {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayCardFrontCommands();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("b")) {
                doBackCard(deck);
                command = input.next();
                command = command.toLowerCase();
                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCardBackCommands(command, card, deck);
                }
            } else {
                processCardFrontCommands(command, card, deck);
            }
        }
    }

    // MODIFIES: card, deck
    // EFFECTS: displays back of card and marks it as reviewed, then displays commands
    private void doBackCard(FlashcardDeck deck) {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        System.out.println("Back:\n" + card.getBackText());
        card.setAsReviewed();
        displayCardBackCommands();
    }

    // EFFECTS: returns true if deck number is valid, false otherwise
    private boolean checkDeckNum(int deckNum) {
        if (deckNum > decks.size() || deckNum < 0) {
            System.out.println("\nNot a valid deck number...");
            return false;
        }
        FlashcardDeck deck = decks.get(deckNum - 1);
        if (deck.length() == 0) {
            System.out.println("\nNo cards in this deck!");
            return false;
        }
        return true;
    }

    // MODIFIES: card
    // EFFECTS: processes user command when on back of a card
    private void processCardBackCommands(String command, Flashcard card, FlashcardDeck deck) {
        if (command.equals("f")) {
            System.out.println("Front:\n" + card.getFrontText());
        } else if (command.equals("n")) {
            nextCard(deck);
        } else if (command.equals("p")) {
            previousCard(deck);
        } else if (command.equals("e")) {
            int cardNum = deck.getCurrentCardNum();
            card = deck.getCard(cardNum);
            doEditCard(card);
        } else if (command.equals("d")) {
            int cardNum = deck.getCurrentCardNum();
            card = deck.getCard(cardNum);
            deck.deleteCard(card);
            System.out.println("The card has been deleted.");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays card front commands
    private void displayCardFrontCommands() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> see back");
        System.out.println("\tn -> next card");
        System.out.println("\tp -> previous card");
        System.out.println("\te -> edit card");
        System.out.println("\td -> delete card");
        System.out.println("\tq -> quit to main menu");
    }

    // MODIFIES: card
    // EFFECT: processes commands that act on a card
    private void processCardFrontCommands(String command, Flashcard card, FlashcardDeck deck) {
        if (command.equals("n")) {
            nextCard(deck);
        } else if (command.equals("p")) {
            previousCard(deck);
        } else if (command.equals("e")) {
            int cardNum = deck.getCurrentCardNum();
            card = deck.getCard(cardNum);
            doEditCard(card);
        } else if (command.equals("d")) {
            int cardNum = deck.getCurrentCardNum();
            card = deck.getCard(cardNum);
            deck.deleteCard(card);
            System.out.println("The card has been deleted.");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // REQUIRES: deck has at least one card in it
    // EFFECTS: checks if all cards have reviewed and resets it if all reviewed and increases current card by 1,
    // then displays the card's front text
    private void nextCard(FlashcardDeck deck) {
        if (deck.isDeckDone()) {
            deck.resetDeck();
        }
        deck.increaseCurrentCard();
        int cardNum = deck.getCurrentCardNum();
        Flashcard card = deck.getCard(cardNum);
        System.out.println("Front:\n" + card.getFrontText());
    }

    // MODIFIES: this
    // REQUIRES: deck has at least one card in it
    // EFFECTS: checks if all cards have reviewed and resets it if all reviewed and decreases current card by 1,
    // then displays the card's front text
    private void previousCard(FlashcardDeck deck) {
        if (deck.isDeckDone()) {
            deck.resetDeck();
        }
        deck.decreaseCurrentCard();
        int cardNum = deck.getCurrentCardNum();
        Flashcard card = deck.getCard(cardNum);
        System.out.println("Front:\n" + card.getFrontText());
    }

    // EFFECTS: displays card back commands
    private void displayCardBackCommands() {
        System.out.println("\nSelect from:");
        System.out.println("\tf -> see front");
        System.out.println("\tn -> next card");
        System.out.println("\tp -> previous card");
        System.out.println("\te -> edit card");
        System.out.println("\td -> delete card");
        System.out.println("\tq -> quit to main menu");
    }

    // EFFECT: changes the front or back of card to new text given by user
    private void doEditCard(Flashcard card) {
        String command;
        System.out.println("\nSelect from:");
        System.out.println("\tf -> edit front");
        System.out.println("\tb -> edit back");
        command = input.next();
        command = command.toLowerCase();


        processEditCardCommand(command, card);
    }

    // MODIFIES: card
    // EFFECTS: processes card command
    private void processEditCardCommand(String command, Flashcard card) {
        if (command.equals("f")) {
            System.out.println("Enter new front text:");
            command = input.next();
            card.setFrontText(command);
            System.out.println("The front text has been updated to " + command);
        } else if (command.equals("b")) {
            System.out.println("Enter new back text:");
            command = input.next();
            card.setBackText(command);
            System.out.println("The back text has been updated to " + command);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new deck to user's list of decks
    private void addDeck() {
        String command;
        FlashcardDeck newDeck;

        System.out.println("Enter name of new deck:");

        command = input.next();

        newDeck = new FlashcardDeck(command);

        decks.add(newDeck);

    }

    // REQUIRES: at least one existing deck
    // MODIFIES: this
    // EFFECTS: deletes an existing deck from user's list of decks
    private void deleteDeck() {
        String command;

        displayDecks();
        System.out.println("Enter the number of the deck you want to delete:");

        command = input.next();
        int deckNum = Integer.parseInt(command);

        if (deckNum > decks.size() || deckNum < 0) {
            System.out.println("Not a valid deck number...");
            return;
        }

        String name = decks.get(deckNum - 1).getName();
        decks.remove(deckNum - 1);
        System.out.println("The deck \n" + deckNum + ". " + name + "\nhas been deleted.");
    }

    // REQUIRES: at least one existing deck
    // MODIFIES: this
    // EFFECTS: edits a deck based on user commands
    private void editDeck() {
        String command;
        boolean keepGoing = true;

        System.out.println("Enter the number of the deck you want to edit:");

        command = input.next();
        int deckNum = Integer.parseInt(command);
        if (deckNum > decks.size() || deckNum < 0) {
            System.out.println("Not a valid deck number...");
            return;
        }

        FlashcardDeck deck = decks.get(deckNum - 1);

        while (keepGoing) {
            displayEditDeckCommands();

            command = input.next();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processDeckCommands(command, deck);
            }
        }
    }

    // EFFECTS: displays edit deck commands
    private void displayEditDeckCommands() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add card");
        System.out.println("\tn -> edit name");
        System.out.println("\tq -> quit to main menu");
    }

    // EFFECTS: processes edit deck command
    private void processDeckCommands(String command, FlashcardDeck deck) {
        if (command.equals("a")) {
            String frontText;
            String backText;
            System.out.println("Enter front text:");
            frontText = input.next();
            System.out.println("Enter back text:");
            backText = input.next();

            Flashcard newCard = new Flashcard(frontText, backText);
            deck.addCard(newCard);
            System.out.println("Card has been added.");
        } else if (command.equals("n")) {
            String name;
            System.out.println("Enter new name of deck:");
            name = input.next();
            deck.setName(name);
            System.out.println("Deck name has been updated");
        } else {
            System.out.println("Selection not valid...");
        }
    }

}
