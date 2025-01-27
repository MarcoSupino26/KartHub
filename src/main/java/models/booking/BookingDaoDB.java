package models.booking;

import exceptions.DataLoadException;
import models.dao.factory.FactoryDAO;
import models.user.Customer;
import models.user.UserDao;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoDB extends BookingDao {

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
            stmt.setString(3, booking.getUser());
            stmt.setString(4, booking.getDescription());
            stmt.setDouble(5, booking.getCost());
            stmt.setString(6, booking.getShift());
            stmt.setDate(7, java.sql.Date.valueOf(booking.getSelectedDay()));
            stmt.setInt(8, booking.getRental());
            stmt.setInt(9, booking.getPersonal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataLoadException("DB insert error");
        }
    }

    @Override
    public List<BookingInterface> getBookingsByTrack(String trackName) {
        String query = "SELECT id, trackname, usrname, description, cost, shift, selectedDay, rental, personal" +
                " FROM bookings WHERE trackname = ?";
        String usrname = "";
        List<ConcreteBooking> bookings = new ArrayList<>();
        List<BookingInterface> bookingList = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String track = rs.getString("trackname");
                    usrname = rs.getString("usrname");
                    Customer user = new Customer(usrname, null, null);
                    String description = rs.getString("description");
                    double cost = rs.getDouble("cost");
                    String shift = rs.getString("shift");
                    LocalDate selectedDay = rs.getDate("selectedDay").toLocalDate();
                    int rental = rs.getInt("rental");
                    int personal = rs.getInt("personal");

                    ConcreteBooking booking = new ConcreteBooking();
                    booking.setId(id);
                    booking.setTrackName(track);
                    booking.setDescription(description);
                    booking.setBaseCost(cost);
                    booking.setShift(shift);
                    booking.setSelectedDay(selectedDay);
                    booking.setRental(rental);
                    booking.setPersonal(personal);
                    booking.setUser(user.getUsername());
                    bookings.add(booking);
                }
            }

            for(ConcreteBooking booking: bookings){
                UserDao userDao = FactoryDAO.getInstance().createUserDao();
                Customer user = null;
                try {
                    user = (Customer) userDao.getUserByUsername(booking.getUser());
                }catch (DataLoadException e) {
                    System.out.println(e.getMessage());
                }
                booking.setUser(user.getUsername());
                bookingList.add(booking);
            }

        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return bookingList;
    }

    public List<BookingInterface> getBookingsByUser(String usrname) {
        List<BookingInterface> bookings = new ArrayList<>();
        String query = "SELECT id, trackname, usrname, description, cost, shift, selectedDay, rental, personal" +
                " FROM bookings WHERE usrname = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usrname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ConcreteBooking booking = new ConcreteBooking();
                booking.setId(resultSet.getString("id"));
                booking.setTrackName(resultSet.getString("trackname"));
                booking.setUser(resultSet.getString("usrname"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBaseCost(resultSet.getDouble("cost"));
                booking.setShift(resultSet.getString("shift"));
                booking.setSelectedDay(resultSet.getDate("selectedDay").toLocalDate());
                booking.setRental(resultSet.getInt("rental"));
                booking.setPersonal(resultSet.getInt("personal"));

                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return bookings;
    }



}
