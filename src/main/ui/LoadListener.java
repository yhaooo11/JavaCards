package ui;

import model.FlashcardDeck;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;

// Represents the action listener for the load button
public class LoadListener implements ActionListener {
    private static final String JSON_STORE = "./data/decks.json";
    private LinkedList<FlashcardDeck> decks;
    private JsonReader jsonReader;
    private FlashcardAppGUI flashcardAppGUI;
    private JFrame askLoadFrame;

    // EFFECTS: constructs load decks listener with no decks, jsonReader, load frame, and FlashcardAppGUI
    public LoadListener(JFrame askLoadFrame, FlashcardAppGUI flashcardAppGUI) {
        this.decks = null;
        this.jsonReader = new JsonReader(JSON_STORE);
        this.askLoadFrame = askLoadFrame;
        this.flashcardAppGUI = flashcardAppGUI;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: processes action command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadDecks();
            flashcardAppGUI.initGraphics(decks);
            addSaveOnCloseListener();
            askLoadFrame.dispose();
        } else if (e.getActionCommand().equals("don't load")) {
            decks = new LinkedList<>();
            flashcardAppGUI.initGraphics(decks);
            addSaveOnCloseListener();
            askLoadFrame.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds window close listener to flashcardAppGUI that asks user to save before close
    private void addSaveOnCloseListener() {
        flashcardAppGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame remindSaveFrame = new JFrame();
                flashcardAppGUI.createSaveDialog(remindSaveFrame, decks);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads decks from file
    private void loadDecks() {
        try {
            decks = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
