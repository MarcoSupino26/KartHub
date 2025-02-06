package models.booking;

public class RaceDecorator extends BookDecorator{
    private final double raceCost;

    public RaceDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        raceCost = priceModifier;
    }

    @Override
    public double getCost(){
        return super.getCost() + raceCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Gara";
    }
}
