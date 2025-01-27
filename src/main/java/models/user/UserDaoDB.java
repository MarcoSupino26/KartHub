package models.user;

import exceptions.DataLoadException;
import models.booking.BookingDao;
import models.booking.BookingInterface;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoDB extends UserDao {

    @Override
    public void addUser(String username, User user) {
        String query = "INSERT INTO users (usrname, password, type) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE password = VALUES(password), type = VALUES(type)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataLoadException("DB insert error");
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

                    if (type.equals("Proprietario")) {
                        user = new Owner(usrname, passw, type);
                    } else {
                        user = new Customer(usrname, passw, type);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return populateUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE usrname = ?";
        User user = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String usrname = rs.getString("usrname");
                    String passw = rs.getString("password");
                    String type = rs.getString("type");

                    if ("Proprietario".equals(type)) {
                        user = new Owner(usrname, passw, type);
                    } else if ("Cliente".equals(type)) {
                        user = new Customer(usrname, passw, type);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return populateUser(user);
    }

    private User populateUser(User user) {
        if (user.getType().equals("Proprietario")) {
            TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
            Track track = null;
            try {
                //Attenzione qui
                track = trackDao.getTrackByUser(user.getUsername());
                track.setOwner(user);
            }catch (DataLoadException e) {
                System.out.println(e.getMessage());
            }
            ((Owner) user).setTrack(track);
        } else if (user.getType().equals("Cliente")) {
            BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
            List<BookingInterface> bookings = new ArrayList<>();

            try {
                bookings = bookingDao.getBookingsByUser(user.getUsername());
            }catch (DataLoadException e){
                System.out.println(e.getMessage());
            }

            ((Customer) user).setBookings(bookings);
        }
        return user;
    }
}
