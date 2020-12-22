package Game;

import Database.GameDAO;
import Game.Cards.Card;
import Utility.RandomUtility;

import java.util.Random;

import static Game.Agents.Dealer.DEALER_MUST_STAND_VAL;

public class GameCoordinator {

    private static StringBuilder postText = new StringBuilder();
    private static enum PlayerChoices {
        HIT,
        STAND,
        DOUBLE_DOWN
    }
    private static enum GameOverStatus {
        NOT_OVER,
        HOUSE_WINS,
        DRAW,
        PLAYER_WINS
    }

    public static void play() {
        // Is there an on-going game?
        int gameId = GameDAO.getNextGameId() - 1;
        Game game = null;
        if (gameId >= 0) {
            game = GameDAO.loadGame(gameId);
        }
        // if there are no existing games or the latest game is over, make a new one
        if (gameId < 0 || (game != null && game.isGameOver())) {
            game = new Game(gameId + 1);
        }

        // what turn is this?
        switch (game.getNumTurns()) {
            case 0 -> firstTurn(game);
            case 1 -> secondTurn(game);
            case 2 -> thirdTurn(game);
            case 3 -> forthTurn(game);
            case 4 -> fifthTurn(game);
            case 5 -> sixthTurn(game);
        }

        // generate graphics

        // Post to facebook

        // save
        if (game.getNumTurns() == 1) {
            game.save();
        } else {
            game.update();
        }
        System.out.println(postText.toString());
    }

    private static void firstTurn(Game game) {
        // player places a random bet
        int amount = game.getPlayer().makeBet();
        postText.append(String.format("The Player has bet $%d!", amount));
        game.setNextTurn();
    }

    private static void secondTurn(Game game) {
        // dealer deals one card to player, and then one face-down card to themself
        // deal card to player
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);
        // deal to dealer
        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        game.setNextTurn();
    }

    private static void thirdTurn(Game game) {
        // player is dealt a second card and the dealer deals another face-down card. Their first card is then visible
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);

        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        game.setNextTurn();
    }

    public static void forthTurn(Game game) {
        // if we have 21, just stand no matter what
        if (game.getPlayer().maxValue() == 21) {
            stand(game);
            return;
        }
        // check if we've lost
        GameOverStatus status = checkGameOver(game);
        if (status != GameOverStatus.NOT_OVER) {
            game.setGameOver();
            System.out.println(status);
            return;
        }
        Random rand = new Random();
        // get random choice
        PlayerChoices choice = PlayerChoices.values()[rand.nextInt(PlayerChoices.values().length)];
        switch (choice) {
            case HIT -> hit(game);
            case STAND -> stand(game);
            case DOUBLE_DOWN -> doubleDown(game);
        }
    }

    public static void fifthTurn(Game game) {
        // dealer reveals next card.
        // check for dealer victory (if immediate). If not, increment turn count

        GameOverStatus status = checkGameOver(game);
        if (status == GameOverStatus.NOT_OVER) {
            game.setNextTurn();
        } else {
            game.setGameOver();
        }
    }

    public static void sixthTurn(Game game) {
        // If dealer is below 16, hit until they are above 16. Check for bust.
        // check for dealer victory (if immediate). If above 16, increment turn count
        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        GameOverStatus status = checkGameOver(game);
        if (status != GameOverStatus.NOT_OVER) {
            game.setGameOver();
        }
        // continue the sixth turn until over
    }

    private static void hit(Game game) {
        // give the player another card
        System.out.println("hit");
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);
    }

    private static void stand(Game game) {
        System.out.println("stand");
        // increment the turn count so we stop looping over 4th turn
        game.setNextTurn();
    }

    private static void doubleDown(Game game) {
        System.out.println("double");
        // deal another card to player
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);
        // double bet
        game.getPlayer().doubleDown();
        // increment turn count
        game.setNextTurn();
    }

    private static GameOverStatus checkGameOver(Game game) {
        int dealerMaxVal = game.getDealer().maxValue();
        int playerMaxVal = game.getPlayer().maxValue();
        if (dealerMaxVal >= DEALER_MUST_STAND_VAL) {
            if (dealerMaxVal > playerMaxVal) {
                postText.append("House wins!");
                return GameOverStatus.HOUSE_WINS;
            } else if (dealerMaxVal == playerMaxVal) {
                postText.append("Draw!");
                return GameOverStatus.DRAW;
            }
            else if (dealerMaxVal < playerMaxVal) {
                postText.append("Player wins!");
                return GameOverStatus.PLAYER_WINS;
            }
        }
        return GameOverStatus.NOT_OVER;
    }
}
