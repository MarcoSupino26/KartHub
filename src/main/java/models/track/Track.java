package models.track;

import models.slots.TimeSlot;
import models.user.User;

import java.util.List;

public class Track {
    private final String name;
    private final String description;
    private final String address;
    private int availableKarts;
    private List<TimeSlot> timeSlots;
    private User owner;

    public Track(String name, int availableKarts, List<TimeSlot> timeSlots, User owner, String description, String address) {
        this.name = name;
        this.availableKarts = availableKarts;
        this.timeSlots = timeSlots;
        this.owner = owner;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAvailableKarts() {
        return availableKarts;
    }

    public void setAvailableKarts(int availableKarts) {
        this.availableKarts = availableKarts;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
