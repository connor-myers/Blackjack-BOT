package Database;

import Game.Deck;
import Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeckDAO {

    public static int getNextDeckId() {
        int val = -1;
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT MAX(DeckId) + 1 FROM Deck;";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            val = rs.getInt("MAX(DeckId) + 1");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    public static void addDeck(Deck deck) {
        String query = String.format("INSERT INTO `Deck`(`DeckId`,`AvailableCards`,`DealtCards`) VALUES (%d, \"%s\", \"%s\");",
                deck.getId(), deck.availableCardsToString(), deck.dealtCardsToString());
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    }

    public Deck retrieveDeck(int deckId) {
        return null;
    }

    public void updateDeck(Deck deck) {

    }

}
