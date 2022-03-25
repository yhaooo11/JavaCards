package ui;

import model.Flashcard;
import model.FlashcardDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// Represents the action listener for the see all cards action listener
public class SeeAllCardsListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private JList list;

    // EFFECTS: constructs a see all cards listener with given decks and JList
    public SeeAllCardsListener(LinkedList<FlashcardDeck> decks, JList list) {
        this.decks = decks;
        this.list = list;
    }

    @Override
    // EFFECTS: processes action command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("See all")) {
            if (list.isSelectionEmpty()) {
                return;
            }
            FlashcardDeck deck = decks.get(list.getSelectedIndex());
            seeAllCards(deck);
        }
    }

    // EFFECTS: displays of cards of given deck on frame
    private void seeAllCards(FlashcardDeck deck) {
        JFrame seeAllCardsFrame = new JFrame("All cards");
        seeAllCardsFrame.setLocationRelativeTo(null);
        seeAllCardsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        seeAllCardsFrame.setResizable(false);
        seeAllCardsFrame.setMinimumSize(new Dimension(500, 300));
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < deck.length(); i++) {
            Flashcard card = deck.getCard(i);
            String cardInfo = "CARD " + i + ": \n Front: " + card.getFrontText() + " | Back: " + card.getBackText();
            JPanel cardPanel = new JPanel();
            cardPanel.add(new JLabel(cardInfo));
            outerPanel.add(cardPanel);
        }

        seeAllCardsFrame.add(outerPanel);
        seeAllCardsFrame.pack();
        seeAllCardsFrame.setVisible(true);

    }
}
