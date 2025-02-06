package models.booking;

public class ChampagneDecorator extends BookDecorator {
    private final double champagneCost;

    public ChampagneDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        champagneCost = priceModifier;
    }

    @Override
    public double getCost() {
        return super.getCost() + champagneCost;
    }

    @Override
    public String getDescription(){
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Champagne";
    }
}
