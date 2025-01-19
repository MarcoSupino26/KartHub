package models.track;

import javafx.scene.image.Image;
import models.slots.TimeSlot;
import models.user.User;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Track {
    private String name;
    private String description;
    private int availableKarts;
    private HashMap<LocalDate, List<TimeSlot>> timeSlots;
    private User owner;
    private String address;
    private Image image;
    private List<Double> cost;
    private double openingHour;
    private double closingHour;
    private double shiftDuration;

    public Track(){
        timeSlots = new HashMap<>();
        cost = new ArrayList<>();
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

    public List<TimeSlot> getTimeSlots(LocalDate date) {
        if(!timeSlots.containsKey(date)){
            return null;
        }
        return timeSlots.get(date);
    }

    public void addTimeSlots(List<TimeSlot> timeSlots, LocalDate date) {
        this.timeSlots.put(date, timeSlots);
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

    public void setCost(List<Double> cost){
        this.cost = cost;
    }

    public double getCost(int i){
        return cost.get(i);
    }

    public Image getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setShiftDuration(double shiftDuration) {this.shiftDuration = shiftDuration;}

    public void setOpeningHour(double openingHour) {this.openingHour = openingHour;}

    public void setClosingHour(double closingHour) {this.closingHour = closingHour;}

    public double getOpeningHour() {return openingHour;}

    public double getClosingHour() {return closingHour;}

    public double getShiftDuration() {return shiftDuration;}
}
