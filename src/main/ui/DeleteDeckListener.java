package ui;

import model.FlashcardDeck;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class DeleteDeckListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private FlashcardAppGUI mainFrame;
    private JList list;

    public DeleteDeckListener(LinkedList<FlashcardDeck> decks, JList list, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.list = list;
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Delete deck")) {
            deleteDeck();
        }
    }

    private void deleteDeck() {
        if (list.isSelectionEmpty()) {
            return;
        }
        decks.remove(list.getSelectedIndex());
        mainFrame.updateDecksList();
        mainFrame.repaint();
    }
}
