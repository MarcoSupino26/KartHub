package models.user;

import utils.DBConnection;  // Importa la classe DBConnection

import java.sql.*;

public class UserDaoDB extends UserDao {

    public UserDaoDB() {}

    @Override
    public void addUser(String usrname, User user) {
        String query = "INSERT INTO users (usrname, password, type) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "password = VALUES(password), " +
                "type = VALUES(type)";

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());  // Cambiato da username a usrname
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String usrname, String password) {
        String query = "SELECT * FROM users WHERE usrname = ? AND password = ?";  // Cambiato da username a usrname

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, usrname);  // Cambiato da username a usrname
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    return new User(usrname, password, type);  // Cambiato da username a usrname
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*@Override
    public User getUserByUsername(String usrname) {
        String query = "SELECT * FROM users WHERE usrname = ?";  // Cambiato da username a usrname

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usrname);  // Cambiato da username a usrname
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    String type = resultSet.getString("type");
                    return new User(usrname, password, type);  // Cambiato da username a usrname
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero dell'utente con username " + usrname, e);
        }
        return null; // Nessun utente trovato
    }*/
    @Override
    public User getUserByUsername(String usrname) {
        String query = "SELECT * FROM users WHERE usrname = ?";  // Cambiato da username a usrname
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usrname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    String type = resultSet.getString("type");
                    return new User(usrname, password, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero dell'utente con username " + usrname, e);
        }
        return null;
    }
}
