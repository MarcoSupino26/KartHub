package models.booking;

public abstract class BookDecorator implements BookingInterface {
    protected ConcreteBooking concreteBooking;

    public BookDecorator(ConcreteBooking concreteBooking) {this.concreteBooking = concreteBooking;}

    @Override
    public double getCost() {return concreteBooking.getCost();}

    @Override
    public String getDescription() {return concreteBooking.getDescription();}
}

