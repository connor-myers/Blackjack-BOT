package Database;

import Game.Agents.Player;
import Game.Cards.Deck;
import Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerDAO {

    public static int getNextPlayerId() {
        int val = -1;
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT MAX(PlayerId) + 1 FROM Player;";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            val = rs.getInt("MAX(PlayerId) + 1");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    public static void savePlayer(Player player) {
        String query = String.format("INSERT INTO `Player`(`PlayerId`,`Cards`, `Sitting`, `Bet`, `Balance`) VALUES (%d, \"%s\", %d, %d, %d);",
                player.getId(), Deck.cardsToString(player.getHand()), Boolean.compare(player.isSitting(), false), player.getBet(), player.getBalance());
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Player loadPlayer(int playerId) {
        Player player = null;
        String query = String.format("SELECT * FROM Player WHERE PlayerId = %d", playerId);
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            player = new Player(playerId, rs.getString("Cards"), rs.getBoolean("Sitting"), rs.getInt("Bet"), rs.getInt("Balance"));
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public static void updatePlayer(Player player) {
        System.out.println(player.isSitting());
        String query = String.format("UPDATE Player SET Cards=\"%s\", Sitting=%d, Bet=%d, Balance=%d WHERE PlayerId = %d",
                Deck.cardsToString(player.getHand()), Boolean.compare(player.isSitting(), false), player.getBet(), player.getBalance(), player.getId());
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
