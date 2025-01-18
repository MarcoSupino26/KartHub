package models.booking;

public class ChampagneDecorator extends BookDecorator {
    private double champagneCost;

    public ChampagneDecorator(ConcreteBooking booking, double priceModifier) {
        super(booking);
        champagneCost = priceModifier;
    }

    public void setChampagneCost(double priceModifier) {
        champagneCost = priceModifier;
    }

    @Override
    public double getCost() {
        return super.getCost() + champagneCost;
    }

    @Override
    public String getDescription(){
        String addedFormat = null;
        if(super.getDescription() != null){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Champagne";
    }
}
