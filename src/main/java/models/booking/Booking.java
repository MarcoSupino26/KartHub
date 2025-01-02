package models.booking;

public class Booking implements BookingInterface {
    private final double baseCost;
    private int id;

    public Booking(double baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    public int getId(){
        return id;
    }
}
