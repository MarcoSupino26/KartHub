package models.booking;

import models.user.User;

public abstract class BookDecorator implements BookingInterface {
    protected BookingInterface booking;

    public BookDecorator(BookingInterface booking) {this.booking = booking;}

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
    public User getUser(){return booking.getUser();}
}

