package Game.Agents;

import Game.Agents.Agent;
import Utility.RandomUtility;

public class Player extends Agent {

    private static final int DEFAULT_BALANCE = 1000; // new players start with $1000

    private int balance; // how much money the player has available to bet
    private int bet;     // the amount the player has bet

    // when receiving from database
    public Player(int id, String hand, boolean sitting, int bet, int balance) {
        super(id, hand, sitting);
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

    public int makeBet() {
       this.bet = (int) Math.floor(RandomUtility.getRandomLogNormal() * this.getBalance()) % 5; // need to save this value
       return bet;
    }

    public void doubleDown() {
        this.bet *= 2;
    }

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
