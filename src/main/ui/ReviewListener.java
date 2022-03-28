package ui;

import model.Flashcard;
import model.FlashcardDeck;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

// Represents the event listener for the review button
public class ReviewListener implements ActionListener {
    private LinkedList<FlashcardDeck> decks;
    private JList list;
    private JFrame frame;
    private FlashcardAppGUI mainFrame;
    private JPanel cardPanel;
    private JPanel buttonPanel;
    private JLabel cardText;
    private JFrame editFrame;
    private JTextField frontTextField;
    private JTextField backTextField;

    private FlashcardDeck deck;

    // EFFECTS: creates a review listener with given flashcard decks, JList, and FlashcardAppGUI
    public ReviewListener(LinkedList<FlashcardDeck> decks, JList list, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.list = list;
        this.mainFrame = mainFrame;
    }


    // MODIFIES: this
    // EFFECTS: processes action command
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Review")) {
            if (list.isSelectionEmpty() || list.getModel().getSize() == 0
                    || decks.get(list.getSelectedIndex()).length() == 0) {
                return;
            }
            this.deck = decks.get(list.getSelectedIndex());
            createReviewWindow();
        } else if (e.getActionCommand().equals("next")) {
            showNextCard();
        } else if (e.getActionCommand().equals("previous")) {
            showPreviousCard();
        } else if (e.getActionCommand().equals("back")) {
            showCardBack();
        } else if (e.getActionCommand().equals("front")) {
            showCardFront();
        } else if (e.getActionCommand().equals("edit")) {
            createEditCardWindow();
        } else if (e.getActionCommand().equals("confirm edit")) {
            confirmEdit();
        } else if (e.getActionCommand().equals("delete")) {
            deleteCard();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates review frame with control buttons and card front text that updates deck list
    // component on close
    private void createReviewWindow() {
        frame = new JFrame("Reviewing " + deck.getName());
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));

        createCardPanel();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.updateDecksList(decks);
                mainFrame.repaint();
            }
        });

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates JPanel with card front text and adds to frame
    private void createCardPanel() {
        cardPanel = new JPanel();
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        cardText = new JLabel(card.getFrontText());
        cardText.setFont(new Font("SansSerif", Font.PLAIN, 24));

        cardPanel.add(cardText);
        cardPanel.setBorder(new EmptyBorder(80,0,0,0));
        cardPanel.setBackground(Color.white);
        createReviewButtonsFront();

        frame.add(cardPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates control buttons (front of card version) and adds to frame
    private void createReviewButtonsFront() {
        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setLayout(new BoxLayout(innerButtonPanel, BoxLayout.X_AXIS));
        innerButtonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton nextButton = new JButton("next card >");
        JButton previousButton = new JButton("< previous card");
        JButton backButton = new JButton("see back");
        JButton editButton = new JButton("edit card");
        JButton deleteButton = new JButton("delete card");

        addReviewButtons(innerButtonPanel, nextButton, previousButton,
                backButton, editButton, deleteButton);

        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);

        previousButton.setActionCommand("previous");
        previousButton.addActionListener(this);

        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        editButton.setActionCommand("edit");
        editButton.addActionListener(this);

        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(innerButtonPanel);
        frame.add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates control buttons (back of card version) and adds to frame
    private void createReviewButtonsBack() {
        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setLayout(new BoxLayout(innerButtonPanel, BoxLayout.X_AXIS));
        innerButtonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton nextButton = new JButton("next card >");
        JButton previousButton = new JButton("< previous card");
        JButton frontButton = new JButton("see front");
        JButton editButton = new JButton("edit card");
        JButton deleteButton = new JButton("delete card");

        addReviewButtons(innerButtonPanel, nextButton, previousButton,
                frontButton, editButton, deleteButton);

        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);

        previousButton.setActionCommand("previous");
        previousButton.addActionListener(this);

        frontButton.setActionCommand("front");
        frontButton.addActionListener(this);

        editButton.setActionCommand("edit");
        editButton.addActionListener(this);

        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(innerButtonPanel);
        frame.add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: innerButtonPanel
    // EFFECTS: adds buttons to button panel
    private void addReviewButtons(JPanel innerButtonPanel, JButton nextButton, JButton previousButton,
                                  JButton backFrontButton, JButton editButton, JButton deleteButton) {
        innerButtonPanel.add(previousButton);
        innerButtonPanel.add(editButton);
        innerButtonPanel.add(backFrontButton);
        innerButtonPanel.add(deleteButton);
        innerButtonPanel.add(nextButton);
    }

    // MODIFIES: this
    // EFFECTS: go to next card and displays front text
    private void showNextCard() {
        Flashcard card = deck.getNextCard();
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.pack();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: go to previous card and displays front text
    private void showPreviousCard() {
        Flashcard card = deck.getPreviousCard();
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.pack();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays card back text and changes buttons to back version
    private void showCardBack() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        card.setAsReviewed();
        cardText.setText(card.getBackText());
        frame.remove(buttonPanel);
        createReviewButtonsBack();
        frame.pack();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays card front text and changes buttons to front version
    private void showCardFront() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.pack();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates edit card window with two text fields and confirm button
    private void createEditCardWindow() {
        editFrame = new JFrame("Edit Card");
        editFrame.setLayout(new BoxLayout(editFrame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel frontTextPanel = new JPanel();
        JPanel backTextPanel = new JPanel();

        frontTextField = new JTextField(12);
        backTextField = new JTextField(12);

        frontTextField.setText(deck.getCard(deck.getCurrentCardNum()).getFrontText());
        backTextField.setText(deck.getCard(deck.getCurrentCardNum()).getBackText());

        frontTextPanel.add(new JLabel("Front Text: "));
        frontTextPanel.add(frontTextField);
        backTextPanel.add(new JLabel("Back Text: "));
        backTextPanel.add(backTextField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setActionCommand("confirm edit");
        confirmButton.addActionListener(this);

        editFrame.add(frontTextPanel);
        editFrame.add(backTextPanel);
        editFrame.add(confirmButton);

        editFrame.setMinimumSize(new Dimension(400, 200));

        editFrame.pack();
        editFrame.setLocationByPlatform(true);
        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: changes card front and back text to text field texts
    private void confirmEdit() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        if (!card.getFrontText().equals(frontTextField.getText())) {
            card.setFrontText(frontTextField.getText());

        }
        if (!card.getBackText().equals(backTextField.getText())) {
            card.setBackText(backTextField.getText());
        }
        editFrame.dispose();
        showCardFront();
    }

    // MODIFIES: this
    // EFFECTS: deletes card, if last card in deck, closes review window
    private void deleteCard() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());

        if (deck.length() == 1) {
            deck.deleteCard(card);
            frame.dispose();
            mainFrame.updateDecksList(decks);
            mainFrame.repaint();
        } else {
            deck.deleteCard(card);
            showCardFront();
        }
    }

}
