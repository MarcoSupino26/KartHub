package models.event;

import exceptions.DataLoadException;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KartEventDaoDB extends KartEventDao {

    public KartEventDaoDB() {
    }

    // Metodo per ottenere eventi associati a un track
    public List<KartEvent> getEventsByTrack(String track) {
        String query = "SELECT * FROM events WHERE trackname = ?";
        List<KartEvent> events = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, track);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    KartEvent kartEvent = new KartEvent(rs.getString("event_name"));
                    kartEvent.setEventType(rs.getString("event_type"));
                    kartEvent.setCost(rs.getDouble("ticket_cost"));
                    kartEvent.setEventDate(rs.getDate("day").toLocalDate());
                    kartEvent.setEventTime(rs.getTime("start_hour").toLocalTime());
                    kartEvent.setTickets(rs.getInt("available_tickets"));
                    kartEvent.setTrackName(rs.getString("trackname"));
                    events.add(kartEvent);
                }
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return events;
    }

    // Metodo per ottenere tutti gli eventi
    public List<KartEvent> getAllEvents() {
        String query = "SELECT * FROM events";
        List<KartEvent> events = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KartEvent kartEvent = new KartEvent(rs.getString("event_name"));
                kartEvent.setEventType(rs.getString("event_type"));
                kartEvent.setCost(rs.getDouble("ticket_cost"));
                kartEvent.setEventDate(rs.getDate("day").toLocalDate());
                kartEvent.setEventTime(rs.getTime("start_hour").toLocalTime());
                kartEvent.setTickets(rs.getInt("available_tickets"));
                kartEvent.setTrackName(rs.getString("trackname"));
                events.add(kartEvent);
            }
        } catch (SQLException e) {
            throw new DataLoadException("DB data retrieval error");
        }
        return events;
    }

    // Metodo per aggiungere o aggiornare un evento
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
