# CPSC 210 Personal Project

## Flashcard  Application

My application will center around the concept
of *flashcards* and allow users to:

- *create/delete/edit* their own decks of flashcards
- *review* their decks of flashcards
- *see statistics* about their progress on decks

As flashcards are primarily used as an aid for memorization, 
this application will be mainly used by *students and those
who need to memorize information.*

I am interested in making a flashcard application because it is
something that I would use personally. Ever since I discovered the 
power of flashcards in grade 11 summer school, I have been using them
any time I need to memorize something.

## User Stories

- As a user, I want to be able to create a new deck of flashcards
- As a user, I want to be able to create a new flashcard/s and add it/them to a deck
- As a user, I want to be able to review a flashcard/s (see front and back, loop through cards)
- As a user, I want to be able to view all my decks of flashcards
- As a user, I want to be able to delete a flashcard or deck
- As a user, I want to be able to edit a flashcard
- As a user, I want to be able to pick up where I last left off on a deck
- As a user, I want to be able to see the number of flashcards I have reviewed in each deck
- As a user, I want to be able to save all decks (and their cards) to file and save automatically on close
- As a user, I want to be able to load my decks from file on start

## Phase 4: Task 2

Sun Mar 27 19:23:46 PDT 2022 \
New deck created \
Sun Mar 27 19:24:00 PDT 2022 \
Flashcard added to deck new deck \
Sun Mar 27 19:24:20 PDT 2022 \
Card deleted from deck new deck \
Sun Mar 27 19:24:27 PDT 2022 \
Flashcard added to deck new deck \
Sun Mar 27 19:24:34 PDT 2022 \
Card front text changed \
Sun Mar 27 19:24:52 PDT 2022 \
New deck created \
Sun Mar 27 19:24:58 PDT 2022 \
Card back text changed \
Sun Mar 27 19:25:01 PDT 2022 \
Saved decks to file

## Phase 4: Task 3
- All listener classes have an association with the FlashcardDeck class. 5/7 have an association 
  with the FlashcardAppGUI class.
  - Many listener classes have common implementations and fields.
  - I would refactor this by adding two abstract classes. The highest one will have an association with the 
    FlashcardDeck class. The other one will extend the highest one and have an association 
    with the FlashcardAppGUI class.
    5/7 listener classes will extend the second one, the other two will extend the first abstract class.
  - Common implementations will be put into the abstract classes (for example, the constructors)
- Another way to refactor is to use the observer pattern on the listener classes (the observables) that have 
  an association with FlashcardAppGUI (the observer)
  This is because these listeners need to update the GUI when a button is clicked

 