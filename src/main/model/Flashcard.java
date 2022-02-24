package model;

/*
Represents a flashcard in a deck.
A flashcard has a front text, back text, and a reviewed status.
The reviewed status is true if the card's front and back as been seen and false otherwise.
 */

import org.json.JSONObject;
import persistence.Writable;

public class Flashcard implements Writable {
    private String frontText;
    private String backText;
    private boolean reviewedStatus;

    // REQUIRES: frontText and backText are not the empty string
    // EFFECTS: constructs a flashcard with the given font and back texts and a reviewed status of false
    public Flashcard(String frontText, String backText) {
        this.frontText = frontText;
        this.backText = backText;
        reviewedStatus = false;
    }

    // REQUIRES: frontText is not the empty string
    // MODIFIES: this
    // EFFECTS: sets flashcard's front text to the given string
    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    // REQUIRES: backText is not the empty string
    // MODIFIES: this
    // EFFECTS: sets flashcard's front text to the given string
    public void setBackText(String backText) {
        this.backText = backText;
    }

    // MODIFIES: this
    // EFFECTS: marks card as reviewed
    public void setAsReviewed() {
        reviewedStatus = true;
    }

    // MODIFIES: this
    // EFFECTS: marks card as not reviewed
    public void setAsNotReviewed() {
        reviewedStatus = false;
    }

    @Override
    // EFFECTS: returns this card as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("front", frontText);
        json.put("back", backText);
        json.put("reviewedStatus", reviewedStatus);
        return json;
    }

    // getters
    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }

    public boolean getReviewedStatus() {
        return reviewedStatus;
    }


}
