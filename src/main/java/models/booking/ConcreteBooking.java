package models.booking;
import java.util.UUID;

public class ConcreteBooking implements BookingInterface {
    private double baseCost;
    private String description;
    private final String id;
    private int rental;
    private int personal;

    public ConcreteBooking() {
        this.id = UUID.randomUUID().toString();
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

    public void setRental(int rental) {this.rental = rental;}

    public void setPersonal(int personal) {this.personal = personal;}

}
