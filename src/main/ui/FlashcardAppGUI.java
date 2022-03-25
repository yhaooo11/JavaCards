package ui;

import model.FlashcardDeck;

// source: https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

// Represents a graphical user interface for a flashcard app
public class FlashcardAppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final String JSON_STORE = "./data/decks.json";
    private LinkedList<FlashcardDeck> decks;

    private JList list;
    private DefaultListModel listModel;

    // EFFECTS: runs the flashcard app GUI by first asking for load
    public FlashcardAppGUI() {
        super("Flashcard App");
        createLoadingScreen();
        askLoad();
    }

    // MODIFIES: remindSaveFrame
    // EFFECTS: Creates dialog that asks user whether they want to save decks to file
    public void createSaveDialog(JFrame remindSaveFrame, LinkedList<FlashcardDeck> decks) {
        JDialog saveDialog = new JDialog(remindSaveFrame);

        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        saveDialog.setLayout(new BorderLayout());

        saveDialog.setMinimumSize(new Dimension(400, 200));
        saveDialog.setResizable(false);

        JLabel remindLabel = new JLabel("Would you like to save?");
        textPanel.add(remindLabel);

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new SaveListener(decks));
        yesButton.setActionCommand("close save");

        JButton noButton = new JButton("No");
        noButton.addActionListener(new SaveListener(decks));
        noButton.setActionCommand("no save");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        saveDialog.add(textPanel, BorderLayout.CENTER);
        saveDialog.add(buttonPanel, BorderLayout.PAGE_END);
        saveDialog.setLocationRelativeTo(null);
        saveDialog.pack();
        saveDialog.setVisible(true);
    }

    // image loading source: https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html
    // EFFECTS: creates splash screen for 4 seconds
    private void createLoadingScreen() {
        JFrame loadingScreenFrame = new JFrame();
        loadingScreenFrame.setLayout(new BorderLayout());
        JPanel loadingTextPanel = new JPanel();
        loadingTextPanel.setBackground(Color.white);
        JLabel loadingLabel = new JLabel("Loading");
        loadingTextPanel.add(loadingLabel);
        BufferedImage loadingScreenImage = null;
        try {
            loadingScreenImage = ImageIO.read(new File("./data/loadingImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(loadingScreenImage));
        loadingScreenFrame.add(picLabel, BorderLayout.CENTER);
        loadingScreenFrame.add(loadingTextPanel, BorderLayout.PAGE_END);
        loadingScreenFrame.pack();
        loadingScreenFrame.setMinimumSize(new Dimension(500,350));
        loadingScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadingScreenFrame.setLocationRelativeTo(null);
        loadingScreenFrame.setVisible(true);
        doLoadAnimation(loadingScreenFrame, loadingLabel);
        loadingScreenFrame.dispose();
    }

    // MODIFIES: loadingLabel
    // EFFECTS: Does ... animation for loading label
    private void doLoadAnimation(JFrame splashScreenFrame, JLabel loadingLabel) {
        try {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    String prevText = loadingLabel.getText();
                    loadingLabel.setText(prevText + ".");
                    splashScreenFrame.pack();
                    splashScreenFrame.repaint();
                    Thread.sleep(500);
                }
                loadingLabel.setText("Loading");
                splashScreenFrame.pack();
                splashScreenFrame.repaint();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this flashcard app will operate
    public void initGraphics(LinkedList<FlashcardDeck> decks) {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createDeckList(decks);
        createButtonPanel(decks);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // source: https://docs.oracle.com/javase/tutorial/displayCode.html?code=
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/
    // components/ListDemo.java
    // MODIFIES: this
    // EFFECTS: Adds list of decks to frame
    private void createDeckList(LinkedList<FlashcardDeck> decks) {
        JPanel listArea = new JPanel();
        listArea.setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        addDecksToList(decks);

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        listArea.add(listScrollPane, BorderLayout.CENTER);

        listArea.setBorder(new EmptyBorder(new Insets(50, 100, 0, 100)));

        add(listArea, BorderLayout.CENTER);
    }

    // MODIFIES: listModel
    // EFFECTS: adds decks to listModel in the form name (# cards reviewed out of # cards)
    public void addDecksToList(LinkedList<FlashcardDeck> decks) {
        for (int i = 0; i < decks.size(); i++) {
            listModel.addElement(decks.get(i).getName() + " ("
                    + decks.get(i).getCardsReviewed() + " out of " + decks.get(i).length() + " reviewed)");
        }
    }

    // MODIFIES: listModel
    // EFFECTS: updates listModel with current decks
    public void updateDecksList(LinkedList<FlashcardDeck> decks) {
        if (listModel.size() != 0) {
            listModel.removeAllElements();
        }
        addDecksToList(decks);
    }

    // MODIFIES: this
    // EFFECTS: adds deck action buttons to frame
    private void createButtonPanel(LinkedList<FlashcardDeck> decks) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton reviewButton = new JButton("Review deck");
        JButton seeAllCardsButton = new JButton("See all cards");
        JButton addButton = new JButton("Add deck");
        JButton deleteButton = new JButton("Delete deck");
        JButton editButton = new JButton("Edit deck");
        JButton saveButton = new JButton("Save decks");

        addActionListeners(decks, reviewButton, seeAllCardsButton, addButton, deleteButton, editButton, saveButton);

        addButtons(buttonPanel, reviewButton, seeAllCardsButton, addButton, deleteButton, editButton, saveButton);

        JPanel outerPanel = new JPanel();
        outerPanel.add(buttonPanel);
        add(outerPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: reviewButton, seeAllCardsButton, addButton, deleteButton, editButton, saveButton
    // EFFECTS: sets action command and adds action listeners to each button
    private void addActionListeners(LinkedList<FlashcardDeck> decks, JButton reviewButton, JButton seeAllCardsButton,
                                    JButton addButton, JButton deleteButton, JButton editButton, JButton saveButton) {
        reviewButton.setActionCommand("Review");
        reviewButton.addActionListener(new ReviewListener(decks, list, this));

        seeAllCardsButton.setActionCommand("See all");
        seeAllCardsButton.addActionListener(new SeeAllCardsListener(decks, list));

        addButton.setActionCommand("Add deck");
        addButton.addActionListener(new AddDeckListener(decks, this));

        deleteButton.setActionCommand("Delete deck");
        deleteButton.addActionListener(new DeleteDeckListener(decks, list,this));

        editButton.setActionCommand("Edit deck");
        editButton.addActionListener(new EditDeckListener(decks, list, this));

        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener(decks));
    }

    // MODIFIES: buttonPanel
    // EFFECTS: adds review, add deck, delete deck, edit deck, and save button to button panel
    private void addButtons(JPanel buttonPanel, JButton reviewButton, JButton seeAllCardsButton, JButton addButton,
                            JButton deleteButton, JButton editButton, JButton saveButton) {
        buttonPanel.add(reviewButton);
        buttonPanel.add(seeAllCardsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
    }

    // EFFECTS: asks users if they want to load decks from file
    private void askLoad() {
        JFrame askLoadFrame = new JFrame("Load decks?");
        askLoadFrame.setLayout(new BorderLayout());
        askLoadFrame.setMinimumSize(new Dimension(400, 200));
        askLoadFrame.setResizable(false);

        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel loadLabel = new JLabel("Would you like to load decks from " + JSON_STORE);
        textPanel.add(loadLabel);

        JButton yesButton = new JButton("Yes");
        yesButton.setActionCommand("load");
        yesButton.addActionListener(new LoadListener(askLoadFrame, this));


        JButton noButton = new JButton("No");
        noButton.setActionCommand("don't load");
        noButton.addActionListener(new LoadListener(askLoadFrame, this));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        askLoadFrame.add(textPanel, BorderLayout.CENTER);
        askLoadFrame.add(buttonPanel, BorderLayout.PAGE_END);
        askLoadFrame.setLocationRelativeTo(null);
        askLoadFrame.pack();
        askLoadFrame.setVisible(true);
    }
}
