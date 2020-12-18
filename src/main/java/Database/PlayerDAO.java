package Database;

import Game.Agents.Player;
import Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerDAO {
    public void savePlayer(Player player) {
        //String query = String.format("INSERT INTO `Player`(`PlayerId`,`Cards`,`Bet`) VALUES (%d, %s, %s);", player.getId(), player.get);
        Connection con = DatabaseManager.getConnection();
        try {
            Statement st = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
