import Database.DealerDAO;
import Database.GameDAO;
import Game.Agents.Dealer;
import Game.Agents.Player;
import Game.Cards.Card;
import Game.*;
import Graphics.Scene;
import Utility.RandomUtility;

public class Main {
    public static void main (String[] args) {
        //Game game = new Game(GameDAO.getNextGameId());
//        game.save();
        GameCoordinator.play();
//        System.out.println(RandomUtility.dist.getSupportUpperBound());
//        for (int i = 0; i < 100; i++) {
//            System.out.println(RandomUtility.getRandomLogNormal());
//        }
    }
}
