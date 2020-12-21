package Game.Agents;

import Game.Agents.Agent;

public class Player extends Agent {

    private static final int DEFAULT_BALANCE = 1000; // new players start with $1000

    private int balance; // how much money the player has available to bet
    private int bet;     // the amount the player has bet

    // when receiving from database
    public Player(int id, String hand, int bet, int balance) {
        super(id, hand);
        this.bet = bet;
        this.balance = balance;
    }

    // when making a new player
    public Player(int id) {
        super(id);
        this.bet = 0; // this constructor is used for generating a new player, so bet is always $0 from here
        this.balance = DEFAULT_BALANCE;
    }

    public int getBalance() {
        return balance;
    }

    public int getBet() {
        return bet;
    }

    // delete


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    @Override
    public String toString() {
        return this.getId() + "\n" + this.getHand() + "\n" + this.getBet() + "\n" + this.getBalance();
    }
}
