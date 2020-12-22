package Game;

import Database.GameDAO;
import Game.Cards.Card;
import Graphics.GameGraphics;

import java.util.Random;

import static Game.Agents.Dealer.DEALER_MUST_STAND_VAL;

public class GameCoordinator {

    private static StringBuilder postText = new StringBuilder();
    private static enum PlayerChoices {
        HIT,
        STAND,
        //DOUBLE_DOWN
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
            case 0:
                firstPost(game);
                break;
            case 1:
                secondPost(game);
                break;
            case 2:
                thirdPost(game);
                break;
            case 3:
                fourthPost(game);
                break;
        }

        // generate graphics
        GameGraphics gameGraphics = new GameGraphics(game);

        // Post to facebook

        // save
        if (game.getNumTurns() == 1) {
            game.save();
        } else {
            game.update();
        }
        System.out.println(postText.toString());
    }

    private static void firstPost(Game game) {
        // dealer deals one card to player, and then one face-down card to themself
        // deal card to player
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);
        // deal to dealer
        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        game.setNextTurn();
    }

    private static void secondPost(Game game) {
        // player is dealt a second card and the dealer deals another face-down card. Their first card is then visible
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);

        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        game.setNextTurn();
    }

    public static void thirdPost(Game game) {
        // if we have 21, just stand no matter what
        if (game.getPlayer().maxValue() == 21) {
            stand(game);
            return;
        }

        Random rand = new Random();
        // get random choice
        PlayerChoices choice = PlayerChoices.values()[rand.nextInt(PlayerChoices.values().length)];
        //case DOUBLE_DOWN -> doubleDown(game);
        switch (choice) {
            case HIT:
                hit(game);
                break;
            case STAND:
                stand(game);
                break;
        }
    }

    public static void fourthPost(Game game) {
        // If dealer is below 16, hit until they are above 16. Check for bust.
        // check for dealer victory (if immediate). If above 16, increment turn count
        Card cardToDealer = game.getDeck().dealCard();
        game.getDealer().getHand().add(cardToDealer);

        GameOverStatus status = checkGameOver(game);
        if (status != GameOverStatus.NOT_OVER) {
            game.setGameOver();
        }

        // continue the fifth turn until over
    }

    private static void hit(Game game) {
        // give the player another card
        System.out.println("hit");
        Card cardToPlayer = game.getDeck().dealCard();
        game.getPlayer().getHand().add(cardToPlayer);
        // check if we busted because of hit
        if (game.getPlayer().maxValue() > 21) {
            game.setGameOver();
        }
    }

    private static void stand(Game game) {
        System.out.println("stand");
        game.getPlayer().sit();
        GameOverStatus status = checkGameOver(game);
        if (status != GameOverStatus.NOT_OVER) {
            game.setGameOver();
        }
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
        if (dealerMaxVal > 21) {
            return GameOverStatus.PLAYER_WINS;
        }
        if (dealerMaxVal >= DEALER_MUST_STAND_VAL) {
            if (dealerMaxVal > playerMaxVal && dealerMaxVal <= 21) {
                postText.append("House wins!");
                return GameOverStatus.HOUSE_WINS;
            } else if (dealerMaxVal == playerMaxVal) {
                postText.append("Draw!");
                return GameOverStatus.DRAW;
            }
            else if (dealerMaxVal < playerMaxVal && playerMaxVal <= 21) {
                postText.append("Player wins!");
                return GameOverStatus.PLAYER_WINS;
            }
        }
        return GameOverStatus.NOT_OVER;
    }
}
