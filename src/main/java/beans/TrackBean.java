package beans;

import javafx.scene.image.Image;
import models.slots.TimeSlot;
import models.user.User;

import java.util.List;

public class TrackBean {
    private static TrackBean instance;
    private String name;
    private User owner;
    private Image image;
    private String description;
    private String address;
    private int availableKarts;
    private String openingHour;
    private String closingHour;
    private int duration;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvailableKarts(int availableKarts) {
        this.availableKarts = availableKarts;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
