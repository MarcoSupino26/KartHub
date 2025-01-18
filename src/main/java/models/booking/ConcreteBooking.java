package models.booking;
import java.util.UUID;

public class ConcreteBooking implements BookingInterface {
    private double baseCost;
    private String description;
    private final String id;

    public ConcreteBooking() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    @Override
    public String getDescription() {return description;}

    public String getId(){return id;}
}
