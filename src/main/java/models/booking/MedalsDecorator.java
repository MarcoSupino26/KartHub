package models.booking;

public class MedalsDecorator extends BookDecorator {
    public MedalsDecorator(Booking booking) {
        super(booking);
    }

    @Override
    public double getCost(){
        return super.getCost() + 10.00;
    }
}
