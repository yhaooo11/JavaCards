package model;

public class Flashcard {
    private String frontText;
    private String backText;

    // REQUIRES: frontText and backText are not the empty string
    // EFFECTS: constructs a flashcard with the given font and back texts
    public Flashcard(String frontText, String backText) {
        this.frontText = frontText;
        this.backText = backText;
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

    // getters
    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }


}
