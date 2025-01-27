package models.event;

import exceptions.DataLoadException;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KartEventDaoDB extends KartEventDao {

    public KartEventDaoDB() {
    }

    public List<KartEvent> getEventsByTrack(String track) {
        String query = "SELECT * FROM events WHERE trackname = ?";
        return getEvents(query, track);
    }

    public List<KartEvent> getAllEvents() {
        String query = "SELECT * FROM events";
        return getEvents(query, null);
    }

    private List<KartEvent> getEvents(String query, String track) {
        List<KartEvent> events = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            if (track != null) {
                stmt.setString(1, track);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(mapToKartEvent(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return events;
    }

    private KartEvent mapToKartEvent(ResultSet rs) throws SQLException {
        KartEvent kartEvent = new KartEvent(rs.getString("event_name"));
        kartEvent.setEventType(rs.getString("event_type"));
        kartEvent.setCost(rs.getDouble("ticket_cost"));
        kartEvent.setEventDate(rs.getDate("day").toLocalDate());
        kartEvent.setEventTime(rs.getTime("start_hour").toLocalTime());
        kartEvent.setTickets(rs.getInt("available_tickets"));
        kartEvent.setTrackName(rs.getString("trackname"));
        return kartEvent;
    }

    public void addKartEvent(KartEvent kartEvent) {
        String query = "INSERT INTO events (event_name, event_type, available_tickets, ticket_cost, day, start_hour, trackname) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE event_type = VALUES(event_type), " +
                "available_tickets = VALUES(available_tickets), " +
                "ticket_cost = VALUES(ticket_cost), " +
                "day = VALUES(day), " +
                "start_hour = VALUES(start_hour), " +
                "trackname = VALUES(trackname)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kartEvent.getEventName());
            stmt.setString(2, kartEvent.getEventType());
            stmt.setInt(3, kartEvent.getTickets());
            stmt.setDouble(4, kartEvent.getCost());
            stmt.setDate(5, Date.valueOf(kartEvent.getEventDate()));
            stmt.setTime(6, Time.valueOf(kartEvent.getEventTime()));
            stmt.setString(7, kartEvent.getTrackName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataLoadException("DB Insert error");
        }
    }
}
