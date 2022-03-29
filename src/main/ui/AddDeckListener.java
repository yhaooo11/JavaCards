package ui;

import model.FlashcardDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// Represents the action listener for the add deck button
public class AddDeckListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private FlashcardAppGUI mainFrame;

    private JTextField nameField;
    private JFrame frame;

    // EFFECTS: constructs add deck action listener with given list of decks and FlashcardAppGUI
    public AddDeckListener(LinkedList<FlashcardDeck> decks, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.mainFrame = mainFrame;
    }

    @Override
    // EFFECTS: processes action command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add deck")) {
            addDeck();
        } else if (e.getActionCommand().equals("confirm add")) {
            doAddDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates add deck frame with text field and add button
    private void addDeck() {
        frame = new JFrame("Add Deck");
        frame.setLayout(new BorderLayout());

        nameField = new JTextField(12);

        JButton addButton = new JButton("Add");
        addButton.setActionCommand("confirm add");
        addButton.addActionListener(this);

        JPanel fieldPanel = new JPanel();
        fieldPanel.add(new JLabel("Name of Deck: "));
        fieldPanel.add(nameField);

        frame.add(fieldPanel, BorderLayout.CENTER);
        frame.add(addButton, BorderLayout.PAGE_END);

        frame.setMinimumSize(new Dimension(400, 100));

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds new deck to list of decks with user inputted name
    private void doAddDeck() {
        String name = nameField.getText();
        if (name.length() == 0) {
            frame.dispose();
            mainFrame.updateDecksList(decks);
            mainFrame.repaint();
            return;
        }
        FlashcardDeck deck = new FlashcardDeck(name);
        decks.add(deck);
        frame.dispose();
        mainFrame.updateDecksList(decks);
        mainFrame.repaint();
    }
}
