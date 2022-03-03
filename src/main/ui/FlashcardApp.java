package ui;

import model.Flashcard;
import model.FlashcardDeck;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/*
Represents the console-based flashcard app. Allows user to add, delete, edit, and review decks
of flashcards.
 */

public class FlashcardApp {
    private static final String JSON_STORE = "./data/decks.json";
    private LinkedList<FlashcardDeck> decks;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

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
                remindSave();
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
    // EFFECTS: initializes user's decks, jsonReader/Writer, and scanner
    private void init() {
        decks = new LinkedList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        loadDecks();
    }

    // MODIFIES: this
    // EFFECTS: loads decks from file
    private void loadDecks() {
        try {
            decks = jsonReader.read();
            System.out.println("Loaded " + decks.size() + " decks from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
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
            System.out.println("\ts -> save decks to file");
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

    // MODIFIES: this
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
        } else if (command.equals("s")) {
            saveDecks();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: asks user if they would like to save decks to file
    private void remindSave() {
        String command;
        System.out.println("Would you like to save your decks to file?");
        System.out.println("\ty -> save");
        System.out.println("\tn -> don't save");
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            saveDecks();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves all decks to file
    private void saveDecks() {
        try {
            jsonWriter.open();
            jsonWriter.write(decks);
            jsonWriter.close();
            System.out.println("Saved " + decks.size() + " decks to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: asks user to choose a deck to review, then cycles through cards of deck
    private void reviewDeck() {
        System.out.println("Choose a deck to review (enter the number):");
        displayDecks();
        String num = input.next();

        int deckNum = Integer.parseInt(num);
        if (!checkDeckNum(deckNum)) {
            return;
        }

        FlashcardDeck deck = decks.get(deckNum - 1);
        int cardNum = deck.getCurrentCardNum();
        Flashcard card = deck.getCard(cardNum);

        System.out.println("Front:\n" + card.getFrontText());

        goThroughCards(deck);

    }

    // EFFECTS: cycles through the cards in a deck with commands on what to do
    private void goThroughCards(FlashcardDeck deck) {
        boolean keepGoing = true;
        while (keepGoing) {
            displayCardFrontCommands();
            String command = input.next();
            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("b")) {
                keepGoing = showBackCard(deck);
            } else {
                keepGoing = processCardFrontCommands(command, deck);
                if (keepGoing) {
                    Flashcard card = deck.getCard(deck.getCurrentCardNum());
                }
            }
        }
    }


    // MODIFIES: deck
    // EFFECTS: displays back of card and marks it as reviewed, then displays commands, processes commands
    private boolean showBackCard(FlashcardDeck deck) {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        System.out.println("Back:\n" + card.getBackText());
        card.setAsReviewed();
        displayCardBackCommands();
        String command = input.next();
        if (command.equals("q")) {
            return false;
        } else {
            boolean keepGoing = processCardBackCommands(command, card, deck);
            if (keepGoing) {
                card = deck.getCard(deck.getCurrentCardNum());
            }
            return keepGoing;
        }
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
    private boolean processCardBackCommands(String command, Flashcard card, FlashcardDeck deck) {
        if (command.equals("f")) {
            System.out.println("Front:\n" + card.getFrontText());
            return true;
        } else if (command.equals("n")) {
            card = deck.getNextCard();
            System.out.println("Front:\n" + card.getFrontText());
            return true;
        } else if (command.equals("p")) {
            card = deck.getPreviousCard();
            System.out.println("Front:\n" + card.getFrontText());
            return true;
        } else if (command.equals("e")) {
            int cardNum = deck.getCurrentCardNum();
            card = deck.getCard(cardNum);
            doEditCard(card);
            return true;
        } else if (command.equals("d")) {
            int cardNum = deck.getCurrentCardNum();
            return deleteCard(cardNum, deck);
        } else {
            System.out.println("Selection not valid...");
            return true;
        }
    }

    // MODIFIES: deck
    // EFFECTS: if deck length = 1, deletes card at cardNum index and returns false. Otherwise, delete card at
    //          cardNum index and return true.
    private boolean deleteCard(int cardNum, FlashcardDeck deck) {
        if (deck.length() == 1) {
            deck.deleteCard(cardNum);
            System.out.println("The card has been deleted.");
            return false;
        } else {
            deck.deleteCard(cardNum);
            System.out.println("The card has been deleted.");
            return true;
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

    // REQUIRES: deck as at least one card in it
    // MODIFIES: card, deck
    // EFFECT: processes commands that act on a card
    private boolean processCardFrontCommands(String command, FlashcardDeck deck) {
        if (command.equals("n")) {
            Flashcard card = deck.getNextCard();
            System.out.println("Front:\n" + card.getFrontText());
            return true;
        } else if (command.equals("p")) {
            Flashcard card = deck.getPreviousCard();
            System.out.println("Front:\n" + card.getFrontText());
            return true;
        } else if (command.equals("e")) {
            int cardNum = deck.getCurrentCardNum();
            Flashcard card = deck.getCard(cardNum);
            doEditCard(card);
            return true;
        } else if (command.equals("d")) {
            int cardNum = deck.getCurrentCardNum();
            return deleteCard(cardNum, deck);
        } else {
            System.out.println("Selection not valid...");
            return true;
        }
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
    // EFFECTS: processes edit card command
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
        String name;
        FlashcardDeck newDeck;

        System.out.println("Enter name of new deck:");

        name = input.next();

        newDeck = new FlashcardDeck(name);

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
    // MODIFIES: deck
    // EFFECTS: edits a deck based on user commands
    private void editDeck() {
        String numInput;
        boolean keepGoing = true;

        System.out.println("Enter the number of the deck you want to edit:");

        numInput = input.next();
        int deckNum = Integer.parseInt(numInput);
        if (deckNum > decks.size() || deckNum < 0) {
            System.out.println("Not a valid deck number...");
            return;
        }

        FlashcardDeck deck = decks.get(deckNum - 1);

        while (keepGoing) {
            displayEditDeckCommands();

            numInput = input.next();
            if (numInput.equals("q")) {
                keepGoing = false;
            } else {
                processEditDeckCommands(numInput, deck);
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

    // MODIFIES: deck
    // EFFECTS: processes edit deck command
    private void processEditDeckCommands(String command, FlashcardDeck deck) {
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
