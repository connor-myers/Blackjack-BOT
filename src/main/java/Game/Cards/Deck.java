package Game.Cards;

import java.io.*;
import java.util.*;

import Game.Cards.Card;
import Game.Cards.Card.*;

import static Game.Cards.Card.Suit.CLUBS;
import static Game.Cards.Card.decodeCardFromString;

public class Deck {
    public static int NUM_CARDS = 52;
    private int id;
    private List<Card> availableCards;
    private List<Card> dealtCards;

    public Deck(int id, String deckFilePath) {
        this.id = id;
        this.dealtCards = new ArrayList<>();
        this.availableCards = loadCardsFromDeck(deckFilePath);
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

    private List<Card> loadCardsFromDeck(String deckFilePath) {
        List<Card> cards = null;
        File deck = new File(deckFilePath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(deckFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String codedCards = br.readLine();
            cards = stringToCards(codedCards);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public static List<Card> stringToCards(String codedCards) {
        List<Card> cards = new ArrayList<>();
        String[] codedCardsSplit = codedCards.split(",");
        for (String codedCard : codedCardsSplit) {
            cards.add(decodeCardFromString(codedCard));
        }

        return cards;
    }

    public static String cardsToString(List<Card> cards) {
        StringBuilder sb = new StringBuilder();
        if (cards.size() == 0) {
            return null;
        }
        for (Card card : cards) {
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