package models.user;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUserDao extends UserDao {

    @Override
    public void addUser(String username, User user) {
        String query = "INSERT INTO users (usrname, password, type) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to database", e);
        }
    }

    @Override
    public User getUser(String username, String password) {
        String query = "SELECT * FROM users WHERE usrname = ? AND password = ?";
        User user = null;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String usrname = rs.getString("usrname");
                    String passw = rs.getString("password");
                    String type = rs.getString("type");
                    user = new User(usrname, passw, type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database", e);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE usrname = ?";
        User user = null;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String usrname = rs.getString("usrname");
                    String passw = rs.getString("password");
                    String type = rs.getString("type");
                    user = new User(usrname, passw, type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user by username from database", e);
        }
        return user;
    }
}
