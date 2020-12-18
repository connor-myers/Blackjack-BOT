package Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    public static int NUM_CARDS = 52;
    private List<Card> availableCards;
    private List<Card> dealtCards;

    public Deck(String deckFilePath) {
        availableCards = new ArrayList<>();
        dealtCards = new ArrayList<>();
        loadCardsFromDeck(deckFilePath);
    }

    private void loadCardsFromDeck(String deckFilePath) {
        File deck = new File(deckFilePath);
    }
}
