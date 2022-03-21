package ui;

import model.FlashcardDeck;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JsonReader jsonReader;

    private JList list;
    private DefaultListModel listModel;

    // EFFECTS: runs the flashcard app GUI (saves decks on close)
    public FlashcardAppGUI() {
        super("Flashcard App");
        initFields();
        createLoadingScreen();
        initGraphics();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SaveListener saveListener = new SaveListener(decks);
                saveListener.saveDecks();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes user's decks from file, and jsonReader/Writer
    private void initFields() {
        decks = new LinkedList<>();
        jsonReader = new JsonReader(JSON_STORE);
        loadDecks();
    }

    // EFFECTS: creates splash screen for 8 seconds
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
                    Thread.sleep(1000);
                }
                loadingLabel.setText("Loading");
                splashScreenFrame.pack();
                splashScreenFrame.repaint();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads decks from file
    private void loadDecks() {
        try {
            decks = jsonReader.read();
            System.out.println("Loaded " + decks.size() + " decks from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        JLabel header = new JLabel("  Current decks loaded from " + JSON_STORE, JLabel.LEFT);
        add(header, BorderLayout.NORTH);
        createDeckList();
        createButtonPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: Adds list of decks to frame
    private void createDeckList() {
        JPanel listArea = new JPanel();
        listArea.setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        addDecksToList();

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        //list.addListSelectionListener(listArea);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        listArea.add(listScrollPane, BorderLayout.CENTER);

        listArea.setBorder(new EmptyBorder(new Insets(50, 100, 0, 100)));

        add(listArea, BorderLayout.CENTER);
    }

    // MODIFIES: listModel
    // EFFECTS: adds decks to listModel in the form name (# cards reviewed out of # cards)
    private void addDecksToList() {
        for (int i = 0; i < decks.size(); i++) {
            listModel.addElement(decks.get(i).getName() + " ("
                    + decks.get(i).getCardsReviewed() + " out of " + decks.get(i).length() + " reviewed)");
        }
    }

    // MODIFIES: listModel
    // EFFECTS: updates listModel with current decks
    public void updateDecksList() {
        listModel.removeAllElements();
        addDecksToList();
    }

    // MODIFIES: this
    // EFFECTS: adds deck action buttons to frame
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));

        JButton reviewButton = new JButton("Review deck");
        reviewButton.setActionCommand("Review");
        reviewButton.addActionListener(new ReviewListener(decks, list, this));

        JButton addButton = new JButton("Add deck");
        addButton.setActionCommand("Add deck");
        addButton.addActionListener(new AddDeckListener(decks, this));

        JButton deleteButton = new JButton("Delete deck");
        deleteButton.setActionCommand("Delete deck");
        deleteButton.addActionListener(new DeleteDeckListener(decks, list,this));

        JButton editButton = new JButton("Edit deck");
        editButton.setActionCommand("Edit deck");
        editButton.addActionListener(new EditDeckListener(decks, list, this));

        JButton saveButton = new JButton("Save decks");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener(decks));

        addButtons(buttonPanel, reviewButton, addButton, deleteButton, editButton, saveButton);
        JPanel outerPanel = new JPanel();
        outerPanel.add(buttonPanel);
        add(outerPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: buttonPanel
    // EFFECTS: adds review, add deck, delete deck, edit deck, and save button to button panel
    private void addButtons(JPanel buttonPanel, JButton reviewButton, JButton addButton,
                            JButton deleteButton, JButton editButton, JButton saveButton) {
        buttonPanel.add(reviewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
    }

}
