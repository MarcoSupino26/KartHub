package models.track;

import javafx.scene.image.Image;
import models.slots.TimeSlot;
import models.user.User;

import java.util.List;

public class Track {
    private String name;
    private String description;
    private int availableKarts;
    private List<TimeSlot> timeSlots;
    private User owner;
    private String address;
    private Image image;

    public Track(){}

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

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
