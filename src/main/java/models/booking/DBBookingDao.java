package models.booking;

import models.dao.factory.FactoryDAO;
import models.user.User;
import models.user.UserDao;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBBookingDao extends BookingDao {

    @Override
    public void addBooking(BookingInterface booking) {
        String query = "INSERT INTO bookings (id, trackname, usrname, description, cost, shift, selectedDay, rental, personal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "trackname = VALUES(trackname), " +
                "usrname = VALUES(usrname), " +
                "description = VALUES(description), " +
                "cost = VALUES(cost), " +
                "shift = VALUES(shift), " +
                "selectedDay = VALUES(selectedDay), " +
                "rental = VALUES(rental), " +
                "personal = VALUES(personal)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, booking.getId());
            stmt.setString(2, booking.getTrackName());
            stmt.setString(3, booking.getUser().getUsername());
            stmt.setString(4, booking.getDescription());
            stmt.setDouble(5, booking.getCost());
            stmt.setString(6, booking.getShift());
            stmt.setDate(7, java.sql.Date.valueOf(booking.getSelectedDay()));
            stmt.setInt(8, booking.getRental());
            stmt.setInt(9, booking.getPersonal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            // Gestione dell'eccezione: stampa del messaggio di errore
            System.err.println("Error adding booking to database: " + e.getMessage());
            // Eventualmente, puoi anche registrare l'errore in un log
        }
    }

    @Override
    public List<BookingInterface> getBookingsByTrack(String trackName) {
        String query = "SELECT * FROM bookings WHERE trackname = ?";
        String usrname = "";
        List<ConcreteBooking> bookings = new ArrayList<>();
        List<BookingInterface> bookingList = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Estrai i valori dal ResultSet prima di chiuderlo
                    String id = rs.getString("id");
                    String track = rs.getString("trackname");
                    usrname = rs.getString("usrname");
                    String description = rs.getString("description");
                    double cost = rs.getDouble("cost");
                    String shift = rs.getString("shift");
                    LocalDate selectedDay = rs.getDate("selectedDay").toLocalDate();
                    int rental = rs.getInt("rental");
                    int personal = rs.getInt("personal");

                    // Ora puoi chiudere il ResultSet e fare altre operazioni
                    ConcreteBooking booking = new ConcreteBooking();
                    booking.setId(id);
                    booking.setTrackName(track);
                    booking.setDescription(description);
                    booking.setBaseCost(cost);
                    booking.setShift(shift);
                    booking.setSelectedDay(selectedDay);
                    booking.setRental(rental);
                    booking.setPersonal(personal);
                    bookings.add(booking);
                }
            }

            for(ConcreteBooking i: bookings){
                // Recupera l'utente dopo aver chiuso il ResultSet
                UserDao userDao = FactoryDAO.getInstance().createUserDao();
                User user = userDao.getUserByUsername(usrname); // Recupera l'utente
                i.setUser(user);
                bookingList.add(i);
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione: stampa del messaggio di errore
            System.err.println("Error retrieving bookings by track: " + e.getMessage());
        }
        return bookingList;
    }


}
