package models.track;

import models.booking.BookingDao;
import models.booking.BookingInterface;
import models.booking.DBBookingDao;
import models.dao.factory.FactoryDAO;
import models.event.DBKartEventDao;
import models.event.KartEvent;
import models.event.KartEventDao;
import models.slots.*;
import models.track.Track;
import models.user.DBUserDao;
import models.user.User;
import models.user.UserDao;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.scene.image.Image;

public class DBTrackDao extends TrackDao {

    public DBTrackDao() {
    }

    @Override
    public void insertTrack(Track track) {
        String query = "INSERT INTO tracks (trackname, description, karts, address, image_path, opening_hour, closing_hour, slot_duration, usrname) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "description = VALUES(description), " +
                "karts = VALUES(karts), " +
                "address = VALUES(address), " +
                "image_path = VALUES(image_path), " +
                "opening_hour = VALUES(opening_hour), " +
                "closing_hour = VALUES(closing_hour), " +
                "slot_duration = VALUES(slot_duration), " +
                "usrname = VALUES(usrname)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Inserting track data
            stmt.setString(1, track.getName());
            stmt.setString(2, track.getDescription());
            stmt.setInt(3, track.getAvailableKarts());
            stmt.setString(4, track.getAddress());
            stmt.setString(5, track.getImage() != null ? track.getImage().getUrl() : null); // Handling image conversion
            stmt.setDouble(6, track.getOpeningHour());
            stmt.setDouble(7, track.getClosingHour());
            stmt.setDouble(8, track.getShiftDuration());
            stmt.setString(9, track.getOwner().getUsername());


            stmt.executeUpdate();


            TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
            List<LocalDate> dates = timeSlotDao.getDatesForTrack(track.getName());
            for (LocalDate date : dates) {
                List<TimeSlot> slots = track.getTimeSlots(date);
                timeSlotDao.insertTimeSlots(slots, date, track.getName());
            }

            // Insert Bookings (optional, depending on your use case)
            List<BookingInterface> bookings = track.allBookings();
            BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
            for (BookingInterface booking : bookings) {
                bookingDao.addBooking(booking);
            }

            List<KartEvent> events = track.allEvents();
            KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
            for (KartEvent event : events) {
                kartEventDao.addKartEvent(event);
            }

            try{
                insertTrackCosts(track);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting or updating track in database", e);
        }
    }

    @Override
    public Track getTrack(String name) {
        String query = "SELECT * FROM tracks WHERE trackname = ?";
        Track track = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    track = new Track();
                    String trackname = rs.getString("trackname");
                    String descritpion = rs.getString("description");
                    int karts  = rs.getInt("karts");
                    String address = rs.getString("address");
                    String image_path = rs.getString("image_path");
                    Image image = new Image(image_path);  // Converting image path to Image object
                    double opening = rs.getDouble("opening_hour");
                    double closing = rs.getDouble("closing_hour");
                    double shiftDuration = rs.getDouble("slot_duration");
                    String username = rs.getString("usrname");

                    track.setName(trackname);
                    track.setDescription(descritpion);
                    track.setAvailableKarts(karts);
                    track.setAddress(address);
                    track.setImage(image);
                    track.setOpeningHour(opening);
                    track.setClosingHour(closing);
                    track.setShiftDuration(shiftDuration);

                    UserDao userDao = FactoryDAO.getInstance().createUserDao();
                    User owner = userDao.getUserByUsername(username);
                    track.setOwner(owner);

                    // Retrieve TimeSlots
                    TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
                    List<LocalDate> timeSlotDates = timeSlotDao.getDatesForTrack(track.getName());
                    for (LocalDate date : timeSlotDates) {
                        List<TimeSlot> timeSlots = timeSlotDao.getTimeSlots(track.getName(), date);
                        track.addTimeSlots(timeSlots, date);
                    }

                    // Retrieve Bookings
                    BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
                    List<BookingInterface> bookings = bookingDao.getBookingsByTrack(track.getName());
                    for (BookingInterface booking : bookings) {
                        track.addBooking(booking);
                    }

                    // Retrieve Kart Events
                    KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
                    List<KartEvent> events = kartEventDao.getEventsByTrack(track.getName());
                    for (KartEvent event : events) {
                        track.addEvent(event);
                    }

                    track.setCost(getTrackCosts(track.getName()));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving track from database", e);
        }

        return track;
    }

    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>();
        String query = "SELECT trackname, description, karts, address, image_path, opening_hour, closing_hour, slot_duration, usrname FROM tracks";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Track track = new Track();
                    track.setName(rs.getString("trackname"));
                    track.setDescription(rs.getString("description"));
                    track.setAvailableKarts(rs.getInt("karts"));
                    track.setAddress(rs.getString("address"));
                    track.setOpeningHour(rs.getDouble("opening_hour"));
                    track.setClosingHour(rs.getDouble("closing_hour"));
                    track.setShiftDuration(rs.getDouble("slot_duration"));
                    track.setOwner(new User(rs.getString("usrname"),null, null));

                    String imagePath = rs.getString("image_path");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        track.setImage(new Image(imagePath));
                    }
                    tracks.add(track);
                }
            }

            tracks = populateTracks(tracks);

        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei tracciati.");
            e.printStackTrace();
        }
        return tracks;
    }

    private List<Track> populateTracks(List<Track> tracks) {
        List<Track> updatedTracks = new ArrayList<>();
        for (Track track : tracks) {
            UserDao userDao = FactoryDAO.getInstance().createUserDao();
            User owner = userDao.getUserByUsername(track.getOwner().getUsername());
            track.setOwner(owner);

            TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
            List<LocalDate> dates = timeSlotDao.getDatesForTrack(track.getName());
            for (LocalDate date : dates) {
                List<TimeSlot> slots = timeSlotDao.getTimeSlots(track.getName(), date);
                track.addTimeSlots(slots, date);
            }

            BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
            List<BookingInterface> bookings = bookingDao.getBookingsByTrack(track.getName());
            for (BookingInterface booking : bookings) {
                track.addBooking(booking);
            }

            KartEventDao eventDao = FactoryDAO.getInstance().createKartEventDao();
            List<KartEvent> events = eventDao.getEventsByTrack(track.getName());
            for (KartEvent event : events) {
                track.addEvent(event);
            }

            try{
                track.setCost(getTrackCosts(track.getName()));
            }catch (SQLException e) {
                e.printStackTrace();
            }

            updatedTracks.add(track);
        }
        return updatedTracks;
    }

    private void insertTrackCosts(Track track) throws SQLException {
        String query = "INSERT INTO cost (trackname, race, quali, fp, champagne, medals, onboard) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "fp = VALUES(fp), " +
                "quali = VALUES(quali), " +
                "champagne = VALUES(champagne), " +
                "medals = VALUES(medals), " +
                "onboard = VALUES(onboard)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, track.getName());
            statement.setDouble(2, track.getCost(1));
            statement.setDouble(3, track.getCost(2));
            statement.setDouble(4, track.getCost(3));
            statement.setDouble(5, track.getCost(4));
            statement.setDouble(6, track.getCost(5));
            statement.setDouble(7, track.getCost(6));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Double> getTrackCosts(String trackName) throws SQLException {
        String query = "SELECT race, quali, fp, champagne, medals, onboard FROM cost WHERE trackname = ?";
        List<Double> costs = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    costs.add(rs.getDouble("race"));
                    costs.add(rs.getDouble("quali"));
                    costs.add(rs.getDouble("fp"));
                    costs.add(rs.getDouble("champagne"));
                    costs.add(rs.getDouble("medals"));
                    costs.add(rs.getDouble("onboard"));
                }
            }
        }
        return costs;
    }
}
