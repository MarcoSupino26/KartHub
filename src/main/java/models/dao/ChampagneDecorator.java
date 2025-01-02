package models.dao;

import models.Booking;

public class ChampagneDecorator extends BookDecorator {
    public ChampagneDecorator(Booking booking) {
        super(booking);
    }

    @Override
    public double getCost() {
        return super.getCost() + 15.00;
    }
}
