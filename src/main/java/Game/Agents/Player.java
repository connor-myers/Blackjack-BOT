package Game.Agents;

import Game.Agents.Agent;

public class Player extends Agent {

    int balance; // how much money the player has available to bet
    int bet;     // the amount the player has bet

    public Player(int id, String hand, int balance, int bet) {
        super(id, hand);
    }

    public Player(int id, int balance) {
        super(id);
        this.bet = 0; // this constructor is used for generating a new player, so bet is always $0 from here
        this.balance = balance;
    }
}
