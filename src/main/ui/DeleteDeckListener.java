package ui;

import model.FlashcardDeck;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// Represents the action listener for the delete deck button
public class DeleteDeckListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private FlashcardAppGUI mainFrame;
    private JList list;

    // EFFECTS: constructs delete deck action listener with given list of decks, JList, and FlashcardAppGUI
    public DeleteDeckListener(LinkedList<FlashcardDeck> decks, JList list, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.list = list;
        this.mainFrame = mainFrame;
    }

    @Override
    // EFFECTS: processes action command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Delete deck")) {
            deleteDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes selected deck from list of decks
    private void deleteDeck() {
        if (list.isSelectionEmpty()) {
            return;
        }
        decks.remove(list.getSelectedIndex());
        mainFrame.updateDecksList();
        mainFrame.repaint();
    }
}
