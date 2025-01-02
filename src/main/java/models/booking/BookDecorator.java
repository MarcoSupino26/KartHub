package models.booking;

public abstract class BookDecorator implements BookingInterface {
    protected Booking booking;

    public BookDecorator(Booking booking) {
        this.booking = booking;
    }

    @Override
    public double getCost() {
        return booking.getCost();
    }
}

