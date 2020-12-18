import Database.DeckDAO;
import Game.Cards.Card;
import Game.Cards.Deck;

public class Main {
    public static void main (String[] args) {
        Deck deck = new Deck(DeckDAO.getNextDeckId(), "standard.deck");
        System.out.println(deck.getAvailableCards());

    }
}
