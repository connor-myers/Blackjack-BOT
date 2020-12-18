package Game.Agents;

import Game.Cards.Card;
import Game.Cards.Deck;
import Game.Cards.Deck.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Agent {

    private int id;
    private List<Card> hand;

    public Agent(int id, String hand) {
        this.id = id;
        this.hand = Deck.stringToCards(hand);
    }

    public Agent(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getId() {
        return id;
    }
}
