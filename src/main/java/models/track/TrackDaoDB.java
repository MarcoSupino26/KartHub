package models.track;

import javafx.scene.image.Image;
import models.booking.BookingDao;
import models.booking.BookingDaoDB;
import models.dao.factory.FactoryDAO;
import models.event.KartEventDao;
import models.event.KartEventDaoDB;
import models.slots.TimeSlotDao;
import models.slots.TimeSlotDaoDB;
import models.track.Track;
import models.booking.BookingInterface;
import models.event.KartEvent;
import models.slots.TimeSlot;
import models.user.User;
import models.user.UserDao;
import models.user.UserDaoDB;
import utils.DBConnection;  // Importa la classe DBConnection
import models.dao.factory.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrackDaoDB extends TrackDao {

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

            stmt.setString(1, track.getName());
            stmt.setString(2, track.getDescription());
            stmt.setInt(3, track.getAvailableKarts());
            stmt.setString(4, track.getAddress());
            stmt.setString(5, track.getImage().getUrl());
            stmt.setDouble(6, track.getOpeningHour());
            stmt.setDouble(7, track.getClosingHour());
            stmt.setDouble(8, track.getShiftDuration());
            stmt.setString(9, track.getOwner().getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gestione del tempo
        TimeSlotDao slotDao = FactoryDAO.getInstance().createTimeSlotDao();
        List<LocalDate> dates = slotDao.getDatesForTrack(track.getName());
        for (LocalDate date : dates) {
            List<TimeSlot> slots = track.getTimeSlots(date);
            slotDao.insertTimeSlots(slots, date, track.getName());
        }

        // Gestione del proprietario
        UserDao userDao = FactoryDAO.getInstance().createUserDao();
        User owner = track.getOwner();
        userDao.addUser(owner.getUsername(), owner);

        // Gestione delle Prenotazioni
        BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
        for (BookingInterface booking : track.allBookings()) {
            bookingDao.addBooking(booking);
        }

        // Gestione degli Eventi
        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        for (KartEvent event : track.allEvents()) {
            kartEventDao.addKartEvent(event);
        }

        // Gestione dei costi
        try {
            insertTrackCosts(track);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public Track getTrack(String trackname) {
        String query = "SELECT * FROM tracks WHERE trackname = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Track track = new Track();
                    track.setName(rs.getString("trackname"));
                    track.setDescription(rs.getString("description"));
                    track.setAvailableKarts(rs.getInt("karts"));
                    track.setAddress(rs.getString("address"));
                    track.setOpeningHour(rs.getDouble("opening_hour"));
                    track.setClosingHour(rs.getDouble("closing_hour"));
                    track.setShiftDuration(rs.getDouble("slot_duration"));

                    // Recupera l'immagine
                    String imagePath = rs.getString("image_path");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        track.setImage(new Image(imagePath));
                    }

                    // Recupera il proprietario
                    UserDao userDao = FactoryDAO.getInstance().createUserDao();
                    User owner = userDao.getUserByUsername(rs.getString("usrname"));
                    track.setOwner(owner);

                    // Recupera le entità correlate
                    TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
                    List<LocalDate> dates = timeSlotDao.getDatesForTrack(trackname);
                    for (LocalDate date : dates) {
                        List<TimeSlot> slots = timeSlotDao.getTimeSlots(trackname, date);
                        track.addTimeSlots(slots, date);
                    }

                    BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
                    List<BookingInterface> bookings = bookingDao.getBookingsByTrack(trackname);
                    for (BookingInterface booking : bookings) {
                        track.addBooking(booking);
                    }

                    KartEventDao eventDao = FactoryDAO.getInstance().createKartEventDao();
                    List<KartEvent> events = eventDao.getEventsByTrack(trackname);
                    for (KartEvent event : events) {
                        track.addEvent(event);
                    }

                    track.setCost(getTrackCosts(trackname));

                    return track;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del tracciato: " + trackname);
            e.printStackTrace();
        }
        return null;
    }*/
    /*@Override
    public Track getTrack(String trackname) {
        String query = "SELECT * FROM tracks WHERE trackname = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Track track = new Track();
                    track.setName(rs.getString("trackname"));
                    track.setDescription(rs.getString("description"));
                    track.setAvailableKarts(rs.getInt("karts"));
                    track.setAddress(rs.getString("address"));
                    track.setOpeningHour(rs.getDouble("opening_hour"));
                    track.setClosingHour(rs.getDouble("closing_hour"));
                    track.setShiftDuration(rs.getDouble("slot_duration"));

                    // Recupera l'immagine
                    String imagePath = rs.getString("image_path");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        track.setImage(new Image(imagePath));
                    }

                    // Recupera il proprietario
                    UserDao userDao = FactoryDAO.getInstance().createUserDao();
                    User owner = userDao.getUserByUsername(rs.getString("usrname"));
                    track.setOwner(owner);

                    // Recupera le entità correlate
                    TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
                    List<LocalDate> dates = timeSlotDao.getDatesForTrack(trackname);
                    for (LocalDate date : dates) {
                        List<TimeSlot> slots = timeSlotDao.getTimeSlots(trackname, date);
                        track.addTimeSlots(slots, date);
                    }

                    BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
                    List<BookingInterface> bookings = bookingDao.getBookingsByTrack(trackname);
                    for (BookingInterface booking : bookings) {
                        track.addBooking(booking);
                    }

                    KartEventDao eventDao = FactoryDAO.getInstance().createKartEventDao();
                    List<KartEvent> events = eventDao.getEventsByTrack(trackname);
                    for (KartEvent event : events) {
                        track.addEvent(event);
                    }

                    track.setCost(getTrackCosts(trackname));
                    return track;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del tracciato: " + trackname);
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public Track getTrack(String trackname) {
        String query = "SELECT * FROM tracks WHERE trackname = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, trackname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Track track = new Track();
                    track.setName(rs.getString("trackname"));
                    track.setDescription(rs.getString("description"));
                    track.setAvailableKarts(rs.getInt("karts"));
                    track.setAddress(rs.getString("address"));
                    track.setOpeningHour(rs.getDouble("opening_hour"));
                    track.setClosingHour(rs.getDouble("closing_hour"));
                    track.setShiftDuration(rs.getDouble("slot_duration"));

                    // Recupera l'immagine
                    String imagePath = rs.getString("image_path");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        track.setImage(new Image(imagePath));
                    }

                    // Recupera il proprietario
                    UserDao userDao = FactoryDAO.getInstance().createUserDao();
                    User owner = userDao.getUserByUsername(rs.getString("usrname"));
                    track.setOwner(owner);

                    // Recupera le entità correlate
                    TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
                    List<LocalDate> dates = timeSlotDao.getDatesForTrack(trackname);
                    for (LocalDate date : dates) {
                        List<TimeSlot> slots = timeSlotDao.getTimeSlots(trackname, date);
                        track.addTimeSlots(slots, date);
                    }

                    BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
                    List<BookingInterface> bookings = bookingDao.getBookingsByTrack(trackname);
                    for (BookingInterface booking : bookings) {
                        track.addBooking(booking);
                    }

                    KartEventDao eventDao = FactoryDAO.getInstance().createKartEventDao();
                    List<KartEvent> events = eventDao.getEventsByTrack(trackname);
                    for (KartEvent event : events) {
                        track.addEvent(event);
                    }

                    track.setCost(getTrackCosts(trackname));
                    System.out.println("Track " + trackname + " recuperato con successo.");
                    return track;
                } else {
                    System.out.println("Track non trovato per nome: " + trackname);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del tracciato: " + trackname);
            e.printStackTrace();
        }
        return null;
    }

    /*@Override
    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>();
        String query = "SELECT trackname FROM tracks";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("trackname");
                Track track = getTrack(name);
                if (track != null) {
                    tracks.add(track);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }*/

    @Override
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
            if(rs.next()) {
                Track track = new Track();
                track.setName(rs.getString("trackname"));
                track.setDescription(rs.getString("description"));
                track.setAvailableKarts(rs.getInt("karts"));
                track.setAddress(rs.getString("address"));
                track.setOpeningHour(rs.getDouble("opening_hour"));
                track.setClosingHour(rs.getDouble("closing_hour"));
                track.setShiftDuration(rs.getDouble("slot_duration"));

                // Recupera l'immagine
                String imagePath = rs.getString("image_path");
                if (imagePath != null && !imagePath.isEmpty()) {
                    track.setImage(new Image(imagePath));
                }

                // Recupera il proprietario
                UserDao userDao = FactoryDAO.getInstance().createUserDao();
                User owner = userDao.getUserByUsername(rs.getString("usrname"));
                track.setOwner(owner);

                // Recupera le entità correlate
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

                track.setCost(getTrackCosts(track.getName()));
                System.out.println("Track " + track.getName() + " recuperato con successo.");
                tracks.add(track);
            }
        }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei tracciati.");
            e.printStackTrace();
        }
        System.out.println("Totale tracciati recuperati: " + tracks.size());
        return tracks;
    }

        private void insertTrackCosts(Track track) throws SQLException {
            String sql = "INSERT INTO cost (trackname, race, fp, quali, champagne, medals, onboard) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "fp = VALUES(fp), " +
                    "quali = VALUES(quali), " +
                    "champagne = VALUES(champagne), " +
                    "medals = VALUES(medals), " +
                    "onboard = VALUES(onboard)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, track.getName());
            stmt.setDouble(2, track.getCost(1)); // race
            stmt.setDouble(3, track.getCost(2)); // fp
            stmt.setDouble(4, track.getCost(3)); // quali
            stmt.setDouble(5, track.getCost(4)); // champagne
            stmt.setDouble(6, track.getCost(5)); // medals
            stmt.setDouble(7, track.getCost(6)); // onboard
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Double> getTrackCosts(String trackName) throws SQLException {
        String sql = "SELECT race, fp, quali, champagne, medals, onboard FROM cost WHERE trackname = ?";
        List<Double> costs = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    costs.add(rs.getDouble("race"));
                    costs.add(rs.getDouble("fp"));
                    costs.add(rs.getDouble("quali"));
                    costs.add(rs.getDouble("champagne"));
                    costs.add(rs.getDouble("medals"));
                    costs.add(rs.getDouble("onboard"));
                }
            }
        }
        while (costs.size() < 6) {
            costs.add(0.0);  // Aggiungi 0.0 per valori predefiniti
        }
        return costs;
    }
}
