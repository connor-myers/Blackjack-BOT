package Game.Agents;

import Game.Cards.Card;
import Game.Cards.Deck;

import java.util.ArrayList;
import java.util.List;

public abstract class Agent {

    private final int id;
    private List<Card> hand;
    private boolean sitting;

    public Agent(int id, String hand, boolean sitting) {
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

    public int maxValue() {
        // returns highest value possible from cards that isn't bust.
        // if not possible, will return a busted value (e.g. 22)
        int numAces = 0;
        int val = 0;
        for (Card card : hand) {
            if (card.getType() == Card.Type.ACE) {
                numAces++;
            }
            val += card.getValue();
        }
        // we only have to do weird calculations if they have aces and we busted
        if (numAces == 0 || val < 21) {
            return val;
        }

        while (val > 21 && numAces > 0) {
            val -= 10;
            numAces--;
        }

        return val;
    }

    public boolean isBust() {
        int val = maxValue();
        return val > 21;
    }

    public int getId() {
        return id;
    }

    public boolean isSitting() {
        return sitting;
    }
}
