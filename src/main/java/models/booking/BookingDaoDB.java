package models.booking;

import models.dao.factory.FactoryDAO;
import models.user.User;
import models.user.UserDao;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoDB extends BookingDao {

    private static final String INSERT_BOOKING_QUERY =
            "INSERT INTO bookings (id, trackname, usrname, description, cost, shift, selectedDay) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "trackname = VALUES(trackname), " +
                    "usrname = VALUES(usrname), " +
                    "description = VALUES(description), " +
                    "cost = VALUES(cost), " +
                    "shift = VALUES(shift), " +
                    "selectedDay = VALUES(selectedDay)";


    private static final String SELECT_BOOKINGS_BY_TRACK_QUERY =
            "SELECT * FROM bookings WHERE trackname = ?";

    public BookingDaoDB() {
        super();
    }

    @Override
    public void addBooking(BookingInterface booking) {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING_QUERY)) {
            setAddBookingParameters(statement, booking);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Errore durante l'inserimento della prenotazione");
        }
    }

    private void setAddBookingParameters(PreparedStatement statement, BookingInterface booking) throws SQLException {
        statement.setString(1, booking.getId());
        statement.setString(2, booking.getTrackName());
        statement.setString(3, booking.getUser().getUsername()); // Cambiato da username a usrname
        statement.setString(4, booking.getDescription());
        statement.setDouble(5, booking.getCost());
        statement.setString(6, booking.getShift());
        statement.setDate(7, Date.valueOf(booking.getSelectedDay()));
    }

    @Override
    public List<BookingInterface> getBookingsByTrack(String trackName) {
        List<BookingInterface> bookings = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKINGS_BY_TRACK_QUERY)) {
            statement.setString(1, trackName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    bookings.add(mapRowToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Errore durante il recupero delle prenotazioni per il tracciato " + trackName);
        }
        return bookings;
    }

    private BookingInterface mapRowToBooking(ResultSet resultSet) throws SQLException {
        ConcreteBooking booking = new ConcreteBooking();
        booking.setTrackName(resultSet.getString("trackname"));
        booking.setDescription(resultSet.getString("description"));
        booking.setShift(resultSet.getString("shift"));
        booking.setSelectedDay(resultSet.getDate("selectedDay").toLocalDate());
        booking.setRental(0); // Impostazioni predefinite, aggiornare se necessario
        booking.setPersonal(0); // Impostazioni predefinite, aggiornare se necessario

        String usrname = resultSet.getString("usrname");  // Cambiato da username a usrname
        UserDao userDao = FactoryDAO.getInstance().createUserDao();
        User user = userDao.getUserByUsername(usrname); // Recupera i dettagli dell'utente
        booking.setUser(user);

        return booking;
    }

    private void handleSQLException(SQLException e, String message) {
        e.printStackTrace();
        throw new RuntimeException(message, e);
    }
}
