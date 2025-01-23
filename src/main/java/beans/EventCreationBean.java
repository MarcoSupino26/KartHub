package beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventCreationBean {
    private final String name;
    private String type;
    private double price;
    private LocalDate date;
    private LocalTime time;
    private int availableTickets;
    private String track;

    public EventCreationBean(String name) {
        this.name = name;
    }

    public String getEventName(){return name;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

}
