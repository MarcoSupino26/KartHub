package models.slots;

import exceptions.DataLoadException;
import utils.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDaoDB extends TimeSlotDao {

    @Override
    public List<TimeSlot> getTimeSlots(String trackName, LocalDate date) {
        String query = "SELECT day, trackname, start_hour, end_hour, available " +
                "FROM time_slots WHERE trackname = ? AND day = ?";
        List<TimeSlot> timeSlots = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackName);
            stmt.setDate(2, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Double startTime = rs.getDouble("start_hour");
                    Double endTime = rs.getDouble("end_hour");
                    boolean available = rs.getBoolean("available");
                    TimeSlot timeSlot = new TimeSlot(startTime, endTime, available);
                    timeSlots.add(timeSlot);
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return timeSlots;
    }

    @Override
    public List<LocalDate> getDatesForTrack(String trackName) {
        String query = "SELECT DISTINCT day FROM time_slots WHERE trackname = ?";
        List<LocalDate> dates = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trackName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate date = rs.getDate("day").toLocalDate();
                    dates.add(date);
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return dates;
    }

    @Override
    public void insertTimeSlots(List<TimeSlot> timeSlots, LocalDate day, String trackName) {
        String query = "INSERT INTO time_slots (day, trackname, start_hour, end_hour, available) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE available = VALUES(available)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            for (TimeSlot timeSlot : timeSlots) {
                stmt.setDate(1, Date.valueOf(day));
                stmt.setString(2, trackName);
                stmt.setDouble(3, timeSlot.getStartTime());
                stmt.setDouble(4, timeSlot.getEndTime());
                stmt.setBoolean(5, timeSlot.isAvailable());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new DataLoadException("DB insert error");
        }
    }
}

