package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/kart_hub";
    private static final String USER = "root";
    private static final String PASSWORD = "#Egeria02";

    // Costruttore privato per il Singleton
    private DBConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Impossibile connettersi al database", e);
        }
    }

    // Metodo per ottenere l'istanza Singleton
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    // Metodo per ottenere la connessione
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Impossibile ristabilire la connessione al database", e);
            }
        }
        return connection;
    }
}
