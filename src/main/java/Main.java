import Database.DealerDAO;
import Database.GameDAO;
import Game.Agents.Dealer;
import Game.Agents.Player;
import Game.Cards.Card;
import Game.Game;

public class Main {
    public static void main (String[] args) {
        Game game = new Game(GameDAO.getNextGameId());
        game.save();
    }
}
