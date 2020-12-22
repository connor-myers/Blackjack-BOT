package Database;

import Game.Agents.Dealer;
import Game.Cards.Deck;
import Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DealerDAO {

    public static int getNextDealerId() {
        int val = -1;
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT MAX(DealerId) + 1 FROM Dealer;";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            val = rs.getInt("MAX(DealerId) + 1");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    public static void saveDealer(Dealer dealer) {
        String query = String.format("INSERT INTO `Dealer`(`DealerId`,`Cards`, `Sitting`) VALUES (%d, %s, %d);",
                dealer.getId(), Deck.cardsToString(dealer.getHand()), Boolean.compare(dealer.isSitting(), false));
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Dealer loadDealer(int dealerId) {
        Dealer dealer = null;
        String query = String.format("SELECT * FROM Dealer WHERE DealerId = %d", dealerId);
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            dealer = new Dealer(dealerId, rs.getString("Cards"), rs.getBoolean("Sitting"));
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dealer;
    }

    public static void updateDealer(Dealer dealer) {
        String query = String.format("UPDATE Dealer SET Cards=\"%s\", Sitting=%d WHERE DealerId = %d",
                Deck.cardsToString(dealer.getHand()), Boolean.compare(dealer.isSitting(), false), dealer.getId());
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    }
}
