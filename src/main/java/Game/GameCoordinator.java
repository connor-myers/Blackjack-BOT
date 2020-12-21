package Game;

import Database.GameDAO;

public class GameCoordinator {
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

    }
}
