package Database;

import Game.Cards.Deck;
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    public static void saveDeck(Deck deck) {
        String query = String.format("INSERT INTO `Deck`(`DeckId`,`AvailableCards`,`DealtCards`) VALUES (%d, \"%s\", \"%s\");",
                deck.getId(), Deck.cardsToString(deck.getAvailableCards()), Deck.cardsToString(deck.getDealtCards()));
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Deck loadDeck(int deckId) {
        Deck deck = null;
        String query = String.format("SELECT * FROM Deck WHERE DeckId = %d", deckId);
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            deck = new Deck(deckId, rs.getString("AvailableCards"), rs.getString("DealtCards"));
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deck;
    }

    public static void updateDeck(Deck deck) {
        String query = String.format("UPDATE Deck SET AvailableCards=\"%s\", DealtCards=\"%s\" WHERE DeckId = %d",
                Deck.cardsToString(deck.getAvailableCards()), Deck.cardsToString(deck.getDealtCards()), deck.getId());
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
