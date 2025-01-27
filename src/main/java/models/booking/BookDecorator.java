package models.booking;

import models.user.User;

import java.time.LocalDate;

public abstract class BookDecorator implements BookingInterface {
    protected BookingInterface booking;

    protected BookDecorator(BookingInterface booking) {this.booking = booking;}

    @Override
    public double getCost() {return booking.getCost();}

    @Override
    public String getDescription() {return booking.getDescription();}

    @Override
    public String getId() {return booking.getId();}

    @Override
    public int getRental(){return booking.getRental();}

    @Override
    public int getPersonal(){return booking.getPersonal();}

    @Override
    public String getUser(){return booking.getUser();}

    @Override
    public String getShift() {
        return booking.getShift();
    }

    @Override
    public String getTrackName() {
        return booking.getTrackName();
    }

    @Override
    public LocalDate getSelectedDay() {return booking.getSelectedDay();}
}

