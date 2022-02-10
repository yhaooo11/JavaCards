package ui;

import model.Flashcard;
import model.FlashcardDeck;

import java.util.LinkedList;
import java.util.Scanner;

public class FlashcardApp {
    private LinkedList<FlashcardDeck> decks;
    private Scanner input;
    private FlashcardDeck deck;

    // EFFECTS: runs the flashcard application
    public FlashcardApp() {
        runFlashcard();
    }

    // source:
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

        System.out.println("\nGoodbye!");
    }

    // source:
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: initializes user's decks and scanner
    private void init() {
        decks = new LinkedList<>();
        deck = new FlashcardDeck("Deck 1");
        decks.add(deck);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nYour current decks are:");
        displayDecks();
        System.out.println("\nSelect from:");
        System.out.println("\tr -> review a deck");
        System.out.println("\ta -> add a deck");
        System.out.println("\td -> delete a deck");
        System.out.println("\te -> edit a deck");
        System.out.println("\tq -> quit");
    }

    private void displayDecks() {
        for (int i = 0; i < decks.size(); i++) {
            int deckNum = i + 1;
            System.out.println(deckNum + ". " + decks.get(i).getName()
                    + " (" + decks.get(i).getNumCardsReviewed() + " out of " + decks.get(i).length() + " reviewed)");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processMenuCommands(String command) {
        if (command.equals("r")) {
            doReview();
        } else if (command.equals("a")) {
           // doWithdrawal();
        } else if (command.equals("d")) {
            //doTransfer();
        } else if (command.equals("e")) {
            //doEdit();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: allows user to review a deck
    private void doReview() {
        String command;
        boolean keepGoing = true;

        System.out.println("Choose a deck to review (enter the number):");
        displayDecks();

        command = input.next();
        int deckNum = Integer.parseInt(command);

        if (deckNum > decks.size()) {
            System.out.println("Not a valid deck number...");
        }

        FlashcardDeck deck = decks.get(deckNum - 1);

        while (keepGoing) {
            Flashcard card = deck.getNextCard();
            System.out.println(card.getFrontText());
            displayCardCommands();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCardCommands(command);
            }
        }



    }

    private void processCardCommands(String command) {
    }

    // EFFECTS: displays card commands
    private void displayCardCommands() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> see back");
        System.out.println("\te -> edit card");
        System.out.println("\td -> delete card");
        System.out.println("\tq -> quit");
    }

}
