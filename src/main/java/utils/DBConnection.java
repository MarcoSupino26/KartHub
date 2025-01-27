package utils;

import exceptions.DataLoadException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private static String URL;
    private static String USER;
    private static String PASSWORD;


    static {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/dbconfig.properties")) {
            prop.load(input);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
        } catch (IOException e) {
            throw new DataLoadException("File open error");
        }
    }
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
            instance = new DBConnection();
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
