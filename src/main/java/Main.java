import Game.Deck;
import Managers.DatabaseManager;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main (String[] args) {
        Deck deck = new Deck("cards.deck");
    }
}
