package models.track;

import exceptions.DataLoadException;
import models.booking.BookingDao;
import models.booking.BookingInterface;
import models.dao.factory.FactoryDAO;
import models.event.KartEvent;
import models.event.KartEventDao;
import models.slots.*;
import models.user.Owner;
import models.user.User;
import models.user.UserDao;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.scene.image.Image;

public class TrackDaoDB extends TrackDao {
    private static final String INSERR = "DB insert error";
    private static final String TRACK = "trackname";
    private static final String DESCRIPTION = "description";
    private static final String KARTS = "karts";
    private static final String ADDR = "address";
    private static final String IMG = "image_path";
    private static final String OPENING = "opening_hour";
    private static final String CLOSING = "closing_hour";
    private static final String DURATION = "slot_duration";
    private static final String USR = "usrname";

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

            insertTimeSlots(track);
            insertBookings(track);
            insertKartEvents(track);
            insertTrackCosts(track);

        } catch (SQLException e) {
            throw new DataLoadException(INSERR);
        }
    }

    private void insertTimeSlots(Track track) {
        TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
        List<LocalDate> dates = track.allDates();
        for (LocalDate date : dates) {
            List<TimeSlot> slots = track.getTimeSlots(date);
            try {
                timeSlotDao.insertTimeSlots(slots, date, track.getName());
            } catch (DataLoadException e) {
                System.out.println(INSERR);
            }
        }
    }

    private void insertBookings(Track track) {
        List<BookingInterface> bookings = track.allBookings();
        BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
        for (BookingInterface booking : bookings) {
            try {
                bookingDao.addBooking(booking);
            } catch (DataLoadException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void insertKartEvents(Track track) {
        List<KartEvent> events = track.allEvents();
        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        for (KartEvent event : events) {
            try {
                kartEventDao.addKartEvent(event);
            } catch (DataLoadException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Track getTrack(String name) {
        String query = "SELECT trackname, description, karts, address, " +
                "image_path, opening_hour, closing_hour, slot_duration, usrname " +
                "FROM tracks WHERE trackname = ?";

        Track track = null;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    track = resultSetToTrack(rs);
                    setTrackOwner(track);
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB retrieval error");
        }
        return populateTrack(track);
    }

    private Track resultSetToTrack(ResultSet rs) throws SQLException {
        String trackname = rs.getString(TRACK);
        String description = rs.getString(DESCRIPTION);
        int karts = rs.getInt(KARTS);
        String address = rs.getString(ADDR);
        String imagePath = rs.getString(IMG);
        Image image = new Image(imagePath);
        double opening = rs.getDouble(OPENING);
        double closing = rs.getDouble(CLOSING);
        double shiftDuration = rs.getDouble(DURATION);
        String usrName = rs.getString(USR);

        Track track = new Track();
        track.setName(trackname);
        track.setDescription(description);
        track.setAvailableKarts(karts);
        track.setAddress(address);
        track.setImage(image);
        track.setOpeningHour(opening);
        track.setClosingHour(closing);
        track.setShiftDuration(shiftDuration);
        track.setOwner(new Owner(usrName, null, null));

        return track;
    }

    private void setTrackOwner(Track track) {
        try {
            UserDao userDao = FactoryDAO.getInstance().createUserDao();
            User owner = userDao.getUserByUsername(track.getOwner().getUsername());
            track.setOwner(owner);
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>();
        String query = "SELECT trackname, description, karts, address, " +
                "image_path, opening_hour, closing_hour, slot_duration, usrname " +
                "FROM tracks";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DataLoadException("DB connection error");
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Track track = new Track();
                    track.setName(rs.getString(TRACK));
                    track.setDescription(rs.getString(DESCRIPTION));
                    track.setAvailableKarts(rs.getInt(KARTS));
                    track.setAddress(rs.getString(ADDR));
                    track.setOpeningHour(rs.getDouble(OPENING));
                    track.setClosingHour(rs.getDouble(CLOSING));
                    track.setShiftDuration(rs.getDouble(DURATION));
                    track.setOwner(new Owner(rs.getString(USR),null, null));

                    String imagePath = rs.getString(IMG);
                    if (imagePath != null && !imagePath.isEmpty()) {
                        track.setImage(new Image(imagePath));
                    }
                    tracks.add(track);
                }
            }

            tracks = populateTracks(tracks);

        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return tracks;
    }

    private Track populateTrack(Track track) {
        TimeSlotDao timeSlotDao = FactoryDAO.getInstance().createTimeSlotDao();
        List<LocalDate> dates = new ArrayList<>();
        try{
            dates = timeSlotDao.getDatesForTrack(track.getName());
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        for (LocalDate date : dates) {
            List<TimeSlot> slots = new ArrayList<>();
            try {
                slots = timeSlotDao.getTimeSlots(track.getName(), date);
            }catch (DataLoadException e){
                System.out.println(e.getMessage());
            }
            track.addTimeSlots(slots, date);
        }

        BookingDao bookingDao = FactoryDAO.getInstance().createBookingDao();
        List<BookingInterface> bookings = new ArrayList<>();
        try{
            bookings = bookingDao.getBookingsByTrack(track.getName());
        } catch (DataLoadException e){
            System.out.println(e.getMessage());
        }

        for (BookingInterface booking : bookings) {
            track.addBooking(booking);
        }

        KartEventDao eventDao = FactoryDAO.getInstance().createKartEventDao();
        List<KartEvent> events = new ArrayList<>();
        try{
            events = eventDao.getEventsByTrack(track.getName());
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }

        for (KartEvent event : events) {
            track.addEvent(event);
        }

        try{
            track.setCost(getTrackCosts(track.getName()));
        }catch (SQLException e) {
            throw new DataLoadException("DB retrieval error");
        }
        return track;
    }

    private List<Track> populateTracks(List<Track> tracks) {
        List<Track> populatedTracks = new ArrayList<>();
        for (Track track : tracks) {
            setTrackOwner(track);
            populatedTracks.add(populateTrack(track));
        }
        return populatedTracks;
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
            throw new DataLoadException(INSERR);
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

    @Override
    public Track getTrackByUser(String username) {
        String query = "SELECT trackname, description, karts, address, " +
                "image_path, opening_hour, closing_hour, slot_duration, usrname " +
                "FROM tracks WHERE usrname = ?";

        Track track = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    track = new Track();
                    String trackname = rs.getString(TRACK);
                    String descritpion = rs.getString(DESCRIPTION);
                    int karts  = rs.getInt(KARTS);
                    String address = rs.getString(ADDR);
                    String imagePath = rs.getString(IMG);
                    Image image = new Image(imagePath);
                    double opening = rs.getDouble(OPENING);
                    double closing = rs.getDouble(CLOSING);
                    double shiftDuration = rs.getDouble(DURATION);
                    String user = rs.getString(USR);

                    track.setName(trackname);
                    track.setDescription(descritpion);
                    track.setAvailableKarts(karts);
                    track.setAddress(address);
                    track.setImage(image);
                    track.setOpeningHour(opening);
                    track.setClosingHour(closing);
                    track.setShiftDuration(shiftDuration);
                    track.setOwner(new Owner(user, null, null));
                }
            }

        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }

        return populateTrack(track);
    }

}
