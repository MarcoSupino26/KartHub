package models.booking;

public class FreePracticeDecorator extends BookDecorator {
    private double fpCost;

    public FreePracticeDecorator(ConcreteBooking booking, double priceModifier) {
        super(booking);
        fpCost = priceModifier;
    }

    public void setPracticeCost(double priceModifier) {fpCost = priceModifier;}

    @Override
    public double getCost(){
        return super.getCost() + fpCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = null;
        if(super.getDescription() != null){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Prova libera";
    }

}
