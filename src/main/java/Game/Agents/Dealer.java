package Game.Agents;

import Game.Agents.Agent;

public class Dealer extends Agent {

    public static final int DEALER_MUST_STAND_VAL = 16;

    public Dealer(int id, String hand, boolean sitting) {
        super(id, hand, sitting);
    }

    public Dealer(int id) {
        super(id);
    }
}
