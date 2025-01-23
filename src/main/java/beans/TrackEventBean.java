package beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrackEventBean {
    private String trackEventName;
    private int eventTickets;
    private LocalDate day;
    private LocalTime startHour;

    public TrackEventBean() {}

    public String getTrackEventName() {
        return trackEventName;
    }

    public void setTrackEventName(String trackEventName) {
        this.trackEventName = trackEventName;
    }

    public int getEventTickets() {
        return eventTickets;
    }

    public void setEventTickets(int eventTickets) {
        this.eventTickets = eventTickets;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }
}
