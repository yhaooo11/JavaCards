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
    // need main frame so i can update decks list.
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

    // creates a review listener with given flashcard decks and JList
    public ReviewListener(LinkedList<FlashcardDeck> decks, JList list, FlashcardAppGUI mainFrame) {
        this.decks = decks;
        this.list = list;
        this.mainFrame = mainFrame;
    }


    // EFFECTS: creates the review window
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Review")) {
            if (list.getModel().getSize() == 0 || decks.get(list.getSelectedIndex()).length() == 0) {
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
        //System.out.println(list.getSelectedIndex());
    }

    private void createReviewWindow() {
        frame = new JFrame("Reviewing " + deck.getName());
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));

        createCardPanel();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("WindowClosingDemo.windowClosing");
                mainFrame.updateDecksList();
                mainFrame.repaint();
                System.out.println(deck.getCardsReviewed());
                //System.exit(0);
            }
        });

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createCardPanel() {
        cardPanel = new JPanel();
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        //JLabel frontText = new JLabel(String.valueOf(list.getSelectedIndex()));
        cardText = new JLabel(card.getFrontText());

        cardPanel.add(cardText);
        cardPanel.setBackground(Color.white);
        createReviewButtonsFront();

        frame.add(cardPanel, BorderLayout.CENTER);
    }

    private void createReviewButtonsFront() {
        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setLayout(new BoxLayout(innerButtonPanel, BoxLayout.X_AXIS));
        innerButtonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton nextButton = new JButton("next card >");
        JButton previousButton = new JButton("< previous card");
        JButton backButton = new JButton("see back");
        JButton editButton = new JButton("edit card");
        JButton deleteButton = new JButton("delete card");

        innerButtonPanel.add(previousButton);
        innerButtonPanel.add(editButton);
        innerButtonPanel.add(backButton);
        innerButtonPanel.add(deleteButton);
        innerButtonPanel.add(nextButton);

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

    private void createReviewButtonsBack() {
        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setLayout(new BoxLayout(innerButtonPanel, BoxLayout.X_AXIS));
        innerButtonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton nextButton = new JButton("next card >");
        JButton previousButton = new JButton("< previous card");
        JButton frontButton = new JButton("see front");
        JButton editButton = new JButton("edit card");
        JButton deleteButton = new JButton("delete card");

        innerButtonPanel.add(previousButton);
        innerButtonPanel.add(editButton);
        innerButtonPanel.add(frontButton);
        innerButtonPanel.add(deleteButton);
        innerButtonPanel.add(nextButton);

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

    private void showNextCard() {
        Flashcard card = deck.getNextCard();
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.repaint();
    }

    private void showPreviousCard() {
        Flashcard card = deck.getPreviousCard();
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.repaint();
    }

    private void showCardBack() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        card.setAsReviewed();
        cardText.setText(card.getBackText());
        frame.remove(buttonPanel);
        createReviewButtonsBack();
        frame.repaint();
    }

    private void showCardFront() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        cardText.setText(card.getFrontText());
        frame.remove(buttonPanel);
        createReviewButtonsFront();
        frame.repaint();
    }

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

    private void confirmEdit() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());
        card.setFrontText(frontTextField.getText());
        card.setBackText(backTextField.getText());
        editFrame.dispose();
        showCardFront();
    }

    private void deleteCard() {
        Flashcard card = deck.getCard(deck.getCurrentCardNum());

        if (deck.length() == 1) {
            deck.deleteCard(card);
            frame.dispose();
            mainFrame.updateDecksList();
            mainFrame.repaint();
        } else {
            deck.deleteCard(card);
            showCardFront();
        }
    }

}
