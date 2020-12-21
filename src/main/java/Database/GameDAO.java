package Database;

import Game.Cards.Deck;
import Game.Game;
import Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDAO {

    public static int getNextGameId() {
        int val = -1;
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT MAX(GameId) + 1 FROM Game;";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            val = rs.getInt("MAX(GameId) + 1");
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    public static void saveGame(Game game) {
        String query = String.format("INSERT INTO `Game`(`GameId`,`DeckId`,`PlayerId`,`DealerId`,`Turn`,`NumTurns`,`GameOver`)" +
                        " VALUES (%d, %d, %d, %d, %d, %d, %d);",
                game.getGameId(),
                game.getDeck().getId(),
                game.getPlayer().getId(),
                game.getDealer().getId(),
                game.getTurn().ordinal(),
                game.getNumTurns(),
                // You'd think a bool could be treated as an int in Java (like C) but nope. This line of code is ridiculous and I hate it. It do work tho 😳
                Boolean.compare(game.isGameOver(), false));
        try {
            Connection con = DatabaseManager.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGame(Game game) {
        String query = String.format("UPDATE Player SET Turn=%d, NumTurns=%d, GameOver=%d WHERE PlayerId = %d",
                game.getTurn().ordinal(), game.getNumTurns(), Boolean.compare(game.isGameOver(), false), game.getGameId());
        try {
            Connection con = DatabaseManager.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Game loadGame(int gameId) {
        Game game = null;
        String query = String.format("SELECT * FROM Game WHERE GameId = %d", gameId);
        try {
            Connection con = DatabaseManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            game = new Game(rs.getInt("GameID"),
                    DeckDAO.loadDeck(rs.getInt("DeckId")),
                    PlayerDAO.loadPlayer(rs.getInt("PlayerId")),
                    DealerDAO.loadDealer(rs.getInt("DealerId")),
                    Game.Turn.values()[rs.getInt("Turn")],
                    rs.getInt("NumTurns"),
                    rs.getInt("GameOver") != 0);
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }
}
