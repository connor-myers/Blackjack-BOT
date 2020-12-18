package Game;

import java.io.*;
import java.util.*;

import Game.Card.*;

import static Game.Card.Suit.CLUBS;

public class Deck {
    public static int NUM_CARDS = 52;
    private int id;
    private List<Card> availableCards;
    private List<Card> dealtCards;

    public Deck(int id, String deckFilePath) {
        this.id = id;
        this.availableCards = new ArrayList<>();
        this.dealtCards = new ArrayList<>();
        loadCardsFromDeck(deckFilePath);
        Collections.shuffle(this.availableCards); // make sure they're in a weird order
    }

    public Deck(int id, String availableCards, String dealtCards) {
        this.id = id;
        this.availableCards = new ArrayList<>();
        this.dealtCards = new ArrayList<>();

        String[] available = availableCards.split(",");
        for (String codedCard : available) {
            if (codedCard.equals("null")) {
                continue;
            }
            this.availableCards.add(decodeCardFromString(codedCard));
        }

        String[] dealt = dealtCards.split(",");
        for (String codedCard : dealt) {
            if (codedCard.equals("null")) {
                continue;
            }
            this.dealtCards.add(decodeCardFromString(codedCard));
        }
    }

    public Card dealCard() {
        Card card = availableCards.get(0);
        availableCards.remove(0);
        dealtCards.add(card);
        return card;
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

    public String availableCardsToString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : availableCards) {
            sb.append(card.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String dealtCardsToString() {
        if (dealtCards.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Card card : dealtCards) {
            sb.append(card.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public List<Card> getDealtCards() {
        return dealtCards;
    }
}