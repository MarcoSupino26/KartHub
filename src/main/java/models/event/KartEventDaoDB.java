package models.event;

import utils.DBConnection;  // Importa la classe DBConnection

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KartEventDaoDB extends KartEventDao {

    public KartEventDaoDB() {
    }

    @Override
    public void addKartEvent(KartEvent kartEvent) {
        String query = "INSERT INTO events (event_name, event_type, available_tickets, ticket_cost, day, start_hour, trackname) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "event_type = VALUES(event_type), " +
                "ticket_cost = VALUES(ticket_cost), " +
                "start_hour = VALUES(start_hour), " +
                "available_tickets = VALUES(available_tickets)";

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
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
            e.printStackTrace();
        }
    }

    @Override
    public List<KartEvent> getAllEvents() {
        List<KartEvent> events = new ArrayList<>();
        String query = "SELECT * FROM events"; // Assicurati che il nome della tabella sia 'events' e non 'KartEvent'

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                KartEvent event = new KartEvent(rs.getString("event_name"));
                event.setEventType(rs.getString("event_type"));
                event.setCost(rs.getDouble("ticket_cost"));
                event.setEventDate(rs.getDate("day").toLocalDate());
                event.setEventTime(rs.getTime("start_hour").toLocalTime());
                event.setTickets(rs.getInt("available_tickets"));
                event.setTrackName(rs.getString("trackname"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    @Override
    public List<KartEvent> getEventsByTrack(String trackName) {
        List<KartEvent> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE trackname = ?"; // Assicurati che il nome della tabella sia 'events'

        try (Connection connection = DBConnection.getInstance().getConnection();  // Ottieni la connessione quando serve
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, trackName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    KartEvent event = new KartEvent(rs.getString("event_name"));
                    event.setEventType(rs.getString("event_type"));
                    event.setCost(rs.getDouble("ticket_cost"));
                    event.setEventDate(rs.getDate("day").toLocalDate());
                    event.setEventTime(rs.getTime("start_hour").toLocalTime());
                    event.setTickets(rs.getInt("available_tickets"));
                    event.setTrackName(rs.getString("trackname"));

                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }
}
