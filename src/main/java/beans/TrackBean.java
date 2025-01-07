package beans;

import models.slots.TimeSlot;
import models.user.User;

import java.util.List;

public class TrackBean {
    private static TrackBean instance;
    private String name;
    private List<TimeSlot> timeSlots;
    private User owner;

    protected TrackBean() {
    }

    public static TrackBean getInstance() {
        if (instance == null) {
            instance = new TrackBean();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
