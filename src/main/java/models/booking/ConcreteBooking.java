package models.booking;
import models.user.User;

import java.time.LocalDate;
import java.util.UUID;

public class ConcreteBooking implements BookingInterface {
    private double baseCost;
    private String description;
    private final String id;
    private int rental;
    private int personal;
    private User user;
    private String shift;
    private String trackName;
    private LocalDate selectedDay;

    public ConcreteBooking() {
        this.id = UUID.randomUUID().toString();
        this.description = "";
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    @Override
    public String getDescription() {return description;}

    @Override
    public String getId(){return id;}

    @Override
    public int getRental() {return rental;}

    @Override
    public int getPersonal() {return personal;}

    @Override
    public User getUser(){return user;}

    public void setUser(User user){this.user = user;}

    public void setRental(int rental) {this.rental = rental;}

    public void setPersonal(int personal) {this.personal = personal;}

    public void setShift(String shift) {this.shift = shift;}

    @Override
    public String getShift(){return shift;}

    @Override
    public String getTrackName(){return trackName;}

    public void setTrackName(String trackName) {this.trackName = trackName;}

    @Override
    public LocalDate getSelectedDay() {return selectedDay;}

    public void setSelectedDay(LocalDate selectedDay) {this.selectedDay = selectedDay;}
}
