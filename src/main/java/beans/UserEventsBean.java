package beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserEventsBean {
    private String event;
    private String type;
    private String place;
    private LocalDate eventDay;
    private LocalTime eventStart;
    private int remainingTickets;
    private double ticketPrice;

    public UserEventsBean(){}

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getEventDay() {
        return eventDay;
    }

    public void setEventDay(LocalDate eventDay) {
        this.eventDay = eventDay;
    }

    public LocalTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalTime eventStart) {
        this.eventStart = eventStart;
    }

    public int getRemainingTickets() {return remainingTickets;}

    public void setRemainingTickets(int remainingTickets) {this.remainingTickets = remainingTickets;}

    public double getTicketPrice() {return ticketPrice;}

    public void setTicketPrice(double ticketPrice) {this.ticketPrice = ticketPrice;}
}
