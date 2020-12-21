package Game;

import Database.DeckDAO;
import Database.PlayerDAO;
import Game.Agents.Dealer;
import Game.Agents.Player;
import Game.Cards.Deck;

public class Game {

    public static enum Turn {
        PLAYER_TURN,
        DEALER_TURN
    }

    private int gameId;
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private Turn turn;
    private int numTurns;
    private boolean gameOver;

    // creating a new game
    public Game(int gameId) {
        this.gameId = gameId;
        this.deck = new Deck(DeckDAO.getNextDeckId(), "cards.deck");
        this.player = new Player(PlayerDAO.getNextPlayerId());
        this.dealer = new Dealer(44); // temp
        this.turn = Turn.DEALER_TURN;
        this.numTurns = 0;
        this.gameOver = false;
    }

    public Game(int gameId, Deck deck, Player player, Dealer dealer, Turn turn, int numTurns, boolean gameOver) {
        this.gameId = gameId;
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.turn = turn;
        this.numTurns = numTurns;
        this.gameOver = gameOver;
    }

    public int getGameId() {
        return gameId;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Turn getTurn() {
        return turn;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
