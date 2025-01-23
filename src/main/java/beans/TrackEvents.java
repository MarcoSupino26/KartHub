package beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrackEvents {
    private String eventName;
    private int tickets;
    private LocalDate eventDate;
    private LocalTime eventTime;

    public TrackEvents(String eventName, int tickets, LocalDate eventDate, LocalTime eventTime) {
        this.eventName = eventName;
        this.tickets = tickets;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public String getEventName() {return eventName;}

    public int getTickets() {return tickets;}

    public LocalDate getEventDate() {return eventDate;}

    public LocalTime getEventTime() {return eventTime;}
}
