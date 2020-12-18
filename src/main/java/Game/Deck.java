package Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Game.Card.*;

import static Game.Card.Suit.CLUBS;

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
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(deckFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String[] cards = br.readLine().split(",");
            for (String codedCard : cards) {
                availableCards.add(decodeCardFromString(codedCard));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Card decodeCardFromString(String code) {
        char suitCode = code.charAt(0);
        String valCode = code.substring(1);

        Suit suit;
        switch (suitCode) {
            case 'C' -> suit = CLUBS;
            case 'D' -> suit = Suit.DIAMONDS;
            case 'S' -> suit = Suit.SPADES;
            case 'H' -> suit = Suit.HEARTS;
            default -> throw new IllegalStateException("Unexpected value: " + suitCode);
        }

        // 1 means 10 here, A means 1 (ace)
        Type type;
        switch (valCode.charAt(0)) {
            case 'A' -> type = Type.ACE;
            case '2', '3', '4', '5', '6', '7', '8', '9', '1' -> type = Type.NUMERIC;
            case 'J' -> type = Type.JACK;
            case 'Q' -> type = Type.QUEEN;
            case 'K' -> type = Type.KING;
            default -> throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }

        int val;
        switch (valCode.charAt(0)) {
            case 'A' -> val = 1;
            case '2', '3', '4', '5', '6', '7', '8', '9' -> val = valCode.charAt(0) - '0';
            case '1', 'J', 'Q', 'K' -> val = 10;
            default -> throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }
        
        return new Card(val, suit, type, code);
    }
}
