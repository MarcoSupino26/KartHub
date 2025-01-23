package models.booking;

public class ChampagneDecorator extends BookDecorator {
    private double champagneCost;

    public ChampagneDecorator(BookingInterface booking, double priceModifier) {
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
        String addedFormat = "";
        if(!super.getDescription().equals("")){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Champagne";
    }
}
