package models.booking;

public class ConcreteBooking implements BookingInterface {
    private double baseCost;
    private String description;
    private int id;

    public ConcreteBooking(double baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    @Override
    public String getDescription() {return description;}

    public int getId(){return id;}
}
