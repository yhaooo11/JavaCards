package persistence;

import model.Flashcard;
import model.FlashcardDeck;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

// Represents a reader that reads decks from JSON data stored in file
public class JsonReader {
    private String source;

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads list of decks from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LinkedList<FlashcardDeck> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfDecks(jsonObject);
    }

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of decks from JSON object and returns it
    private LinkedList<FlashcardDeck> parseListOfDecks(JSONObject jsonObject) {
        LinkedList<FlashcardDeck> listOfDecks = new LinkedList<>();
        addDecks(listOfDecks, jsonObject);
        return listOfDecks;
    }

    // MODIFIES: decks, deck
    // EFFECTS: parses decks from JSON object and adds them to list of decks
    private void addDecks(LinkedList<FlashcardDeck> decks, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("decks");
        for (Object json : jsonArray) {
            JSONObject nextDeck = (JSONObject) json;

            String name = nextDeck.getString("name");
            int currentCard = nextDeck.getInt("currentCard");

            FlashcardDeck deck = new FlashcardDeck(name);
            deck.setCurrentCard(currentCard);

            addCards(deck, nextDeck);
            decks.add(deck);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses cards from JSON object and adds it to deck
    private void addCards(FlashcardDeck deck, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;

            String front = nextCard.getString("front");
            String back = nextCard.getString("back");
            boolean reviewedStatus = nextCard.getBoolean("reviewedStatus");

            Flashcard card = new Flashcard(front, back);
            if (reviewedStatus) {
                card.setAsReviewed();
            }

            deck.addCard(card);
        }
    }
}
