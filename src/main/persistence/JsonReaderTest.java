package persistence;

import model.FlashcardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest {
    private LinkedList<FlashcardDeck> decks;

    @BeforeEach
    void setUp() {
        decks = new LinkedList<>();
    }

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            decks = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderEmptyListOfDecks() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfDecks.json");
        try {
            decks = reader.read();
            assertEquals(0, decks.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListOfDecks() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfDecks.json");
        try {
            decks = reader.read();
            assertEquals(2, decks.size());

            FlashcardDeck deck1 = decks.get(0);
            assertEquals("deck1", deck1.getName());
            assertEquals(0, deck1.getCurrentCardNum());
            assertEquals(1, deck1.length());
            checkCard("front text", "back text", false, deck1.getCard(0));

            FlashcardDeck deck2 = decks.get(1);
            assertEquals("deck2", deck2.getName());
            assertEquals(1, deck2.getCurrentCardNum());
            assertEquals(2, deck2.length());
            checkCard("front text 1", "back text 1", true, deck2.getCard(0));
            checkCard("front text 2", "back text 2", true, deck2.getCard(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
