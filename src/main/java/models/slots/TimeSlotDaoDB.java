package models.slots;

import utils.DBConnection;  // Importa la classe DBConnection

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDaoDB extends TimeSlotDao {

    public TimeSlotDaoDB() {}

    @Override
    public void insertTimeSlots(List<TimeSlot> timeSlots, LocalDate day, String trackName) {
        String query = "INSERT INTO time_slots (day, trackname, start_hour, end_hour, available) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "end_hour = VALUES(end_hour), " +
                "available = VALUES(available)";

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Converti LocalDate in java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(day);

            for (TimeSlot timeSlot : timeSlots) {
                stmt.setDate(1, sqlDate);  // Usa sqlDate al posto di day
                stmt.setString(2, trackName);
                stmt.setDouble(3, timeSlot.getStartTime());
                stmt.setDouble(4, timeSlot.getEndTime());
                stmt.setBoolean(5, timeSlot.isAvailable()); // Si aspetta un booleano per available
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LocalDate> getDatesForTrack(String trackName) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT DISTINCT day FROM time_slots WHERE trackname = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dates.add(rs.getDate("day").toLocalDate());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates;
    }

    @Override
    public List<TimeSlot> getTimeSlots(String trackName, LocalDate date) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String query = "SELECT start_hour, end_hour, available FROM time_slots WHERE trackname = ? AND day = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, trackName);
            stmt.setDate(2, Date.valueOf(date));  // Usa Date.valueOf() per LocalDate
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double startHour = rs.getDouble("start_hour");
                    double endHour = rs.getDouble("end_hour");
                    boolean available = rs.getBoolean("available"); // Gestito come booleano
                    timeSlots.add(new TimeSlot(startHour, endHour, available));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeSlots;
    }
}
