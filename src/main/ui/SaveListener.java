package ui;

import model.FlashcardDeck;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;

// Represents the action listener for the save button
public class SaveListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private static final String JSON_STORE = "./data/decks.json";
    private JsonWriter jsonWriter;

    // EFFECTS: constructs a save button action listener with given list of decks and a jsonWriter
    public SaveListener(LinkedList<FlashcardDeck> decks) {
        this.decks = decks;
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    @Override
    // EFFECTS: processes action command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveDecks();
        } else if (e.getActionCommand().equals("close save")) {
            saveDecks();
            System.exit(0);
        } else if (e.getActionCommand().equals("no save")) {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves all decks to file
    public void saveDecks() {
        try {
            jsonWriter.open();
            jsonWriter.write(decks);
            jsonWriter.close();
            System.out.println("Saved " + decks.size() + " decks to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
