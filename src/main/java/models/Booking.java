package models;

import models.dao.BookingInterface;

public class Booking implements BookingInterface {
    private final double baseCost;

    public Booking(double baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public double getCost() {
        return baseCost;
    }
}
