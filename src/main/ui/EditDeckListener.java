package ui;

import model.Flashcard;
import model.FlashcardDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// Represents the action listener for the edit deck button
public class EditDeckListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private FlashcardAppGUI mainFrame;
    private FlashcardDeck deck;

    private JList list;
    private int selectedIndex;

    private JTextField nameTextField;
    private JFrame editNameFrame;
    private JFrame addCardFrame;
    private JTextField frontTextField;
    private JTextField backTextField;
    private JPanel confirmTextPanel;

    // EFFECTS: constructs an edit deck listener with given decks, JList, selectedIndex of 0, and FlashcardAppGUI
    public EditDeckListener(LinkedList<FlashcardDeck> decks, JList list, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.list = list;
        this.mainFrame = mainFrame;
        this.selectedIndex = 0;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: processes action commands
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Edit deck")) {
            if (list.getModel().getSize() == 0 || list.isSelectionEmpty()) {
                return;
            }
            selectedIndex = list.getSelectedIndex();
            this.deck = decks.get(selectedIndex);
            editDeck();
        } else if (e.getActionCommand().equals("edit name")) {
            editDeckName();
        } else if (e.getActionCommand().equals("confirm name")) {
            changeDeckName();
        } else if (e.getActionCommand().equals("add card")) {
            addCardFrame();
        } else if (e.getActionCommand().equals("confirm add")) {
            addCard();
        }

    }

    // EFFECTS: creates edit deck frame with name of selected deck, and edit name and add card buttons
    private void editDeck() {
        FlashcardDeck deck = decks.get(list.getSelectedIndex());

        JFrame frame = new JFrame("Edit deck");
        frame.setMinimumSize(new Dimension(200, 120));
        frame.setLayout(new BorderLayout());

        JPanel editPanel = new JPanel();

        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.X_AXIS));

        JButton editNameButton = new JButton("Edit name");
        editNameButton.setActionCommand("edit name");
        editNameButton.addActionListener(this);

        JButton addCardsButton = new JButton("Add card");
        addCardsButton.setActionCommand("add card");
        addCardsButton.addActionListener(this);

        editPanel.add(editNameButton);
        editPanel.add(addCardsButton);

        frame.add(editPanel, BorderLayout.CENTER);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Editing " + deck.getName()));

        frame.add(namePanel, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates edit deck name frame with text field and confirm button
    private void editDeckName() {
        editNameFrame = new JFrame();
        editNameFrame.setLayout(new BoxLayout(editNameFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();

        nameTextField = new JTextField(12);
        nameTextField.setText(deck.getName());

        namePanel.add(new JLabel("Deck name:"));
        namePanel.add(nameTextField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setActionCommand("confirm name");
        confirmButton.addActionListener(this);

        editNameFrame.add(namePanel);
        editNameFrame.add(confirmButton);

        editNameFrame.pack();
        editNameFrame.setLocationByPlatform(true);
        editNameFrame.setLocationRelativeTo(null);
        editNameFrame.setVisible(true);
        editNameFrame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: updates deck name and main frame
    private void changeDeckName() {
        String newName = nameTextField.getText();

        deck.setName(newName);

        editNameFrame.dispose();
        mainFrame.updateDecksList(decks);
        mainFrame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates add card frame with 2 text fields and a button
    private void addCardFrame() {
        addCardFrame = new JFrame();

        addCardFrame.setLayout(new BoxLayout(addCardFrame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel frontTextPanel = new JPanel();
        JPanel backTextPanel = new JPanel();


        frontTextField = new JTextField(12);
        backTextField = new JTextField(12);


        frontTextPanel.add(new JLabel("Front Text: "));
        frontTextPanel.add(frontTextField);
        backTextPanel.add(new JLabel("Back Text: "));
        backTextPanel.add(backTextField);

        JButton confirmAddButton = new JButton("Add");
        confirmAddButton.setActionCommand("confirm add");
        confirmAddButton.addActionListener(this);

        confirmTextPanel = new JPanel();

        addCardFrame.add(confirmTextPanel);
        addCardFrame.add(frontTextPanel);
        addCardFrame.add(backTextPanel);
        addCardFrame.add(confirmAddButton);

        addCardFrame.setMinimumSize(new Dimension(400, 150));

        addCardFrame.pack();
        addCardFrame.setLocationByPlatform(true);
        addCardFrame.setLocationRelativeTo(null);
        addCardFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds card with user inputted front and back text to deck
    private void addCard() {
        String frontText = frontTextField.getText();
        String backText = backTextField.getText();

        deck.addCard(new Flashcard(frontText, backText));

        JLabel confirmMessage = new JLabel("Card added");

        confirmTextPanel.removeAll();
        confirmTextPanel.add(confirmMessage);

        addCardFrame.pack();
        addCardFrame.repaint();
        mainFrame.updateDecksList(decks);
        mainFrame.repaint();
    }

}
