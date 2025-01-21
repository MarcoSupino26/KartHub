package models.event;

import java.time.LocalDate;
import java.time.LocalTime;

public class KartEvent {
    private String eventName;
    private String eventType;
    private double cost;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private int tickets;
    private String trackName;

    public KartEvent(String eventName){
        this.eventName = eventName;
    }

    public String getEventName() {return eventName;}

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {this.tickets = tickets;}

    public String getTrackName() {return trackName;}

    public void setTrackName(String trackName) {this.trackName = trackName;}
}
