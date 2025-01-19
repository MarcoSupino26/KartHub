package utils;

import javafx.scene.image.Image;
import models.user.User;

public class ManageSession {
    private String trackName;
    private String address;
    private String description;
    private User owner;
    private Image image;
    private double opening;
    private double closing;
    private double duration;
    private int availableKarts;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getOpening() {
        return opening;
    }

    public void setOpening(double opening) {
        this.opening = opening;
    }

    public double getClosing() {
        return closing;
    }

    public void setClosing(double closing) {
        this.closing = closing;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setAvailableKarts(int availableKarts) {
        this.availableKarts = availableKarts;
    }

    public int getAvailableKarts(){return availableKarts;}
}
