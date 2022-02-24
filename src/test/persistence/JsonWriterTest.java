package persistence;

import model.Flashcard;
import model.FlashcardDeck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    // source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfDecks() {
        try {
            LinkedList<FlashcardDeck> decks = new LinkedList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfDecks.json");
            decks = reader.read();
            assertEquals(0, decks.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListOfDecks() {
        try {
            LinkedList<FlashcardDeck> decks = new LinkedList<>();
            FlashcardDeck deck1 = new FlashcardDeck("deck1");
            Flashcard card = new Flashcard("front text", "back text");
            deck1.addCard(card);
            decks.add(deck1);

            FlashcardDeck deck2 = new FlashcardDeck("deck2");
            deck2.setCurrentCard(1);
            card = new Flashcard("front text 1", "back text 1");
            card.setAsReviewed();
            deck2.addCard(card);
            card = new Flashcard("front text 2", "back text 2");
            card.setAsReviewed();
            deck2.addCard(card);
            decks.add(deck2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralListOfDecks.json");
            decks = reader.read();
            assertEquals(2, decks.size());

            FlashcardDeck deckRead1 = decks.get(0);
            assertEquals("deck1", deckRead1.getName());
            assertEquals(0, deckRead1.getCurrentCardNum());
            assertEquals(1, deckRead1.length());
            checkCard("front text", "back text", false, deckRead1.getCard(0));

            FlashcardDeck deckRead2 = decks.get(1);
            assertEquals("deck2", deckRead2.getName());
            assertEquals(1, deckRead2.getCurrentCardNum());
            assertEquals(2, deckRead2.length());
            checkCard("front text 1", "back text 1", true, deckRead2.getCard(0));
            checkCard("front text 2", "back text 2", true, deckRead2.getCard(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
