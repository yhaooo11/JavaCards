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

public class LoadListener implements ActionListener {
    private static final String JSON_STORE = "./data/decks.json";
    private LinkedList<FlashcardDeck> decks;
    private JsonReader jsonReader;
    private FlashcardAppGUI flashcardAppGUI;
    private JFrame askLoadFrame;

    public LoadListener(JFrame askLoadFrame, FlashcardAppGUI flashcardAppGUI) {
        this.decks = null;
        this.jsonReader = new JsonReader(JSON_STORE);
        this.askLoadFrame = askLoadFrame;
        this.flashcardAppGUI = flashcardAppGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadDecks();
            System.out.println(decks.size());
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
            //flashcardAppGUI.setDeck(decks);
            System.out.println("Loaded " + decks.size() + " decks from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
