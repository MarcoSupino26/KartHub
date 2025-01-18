package models.booking;

public class QualifyingDecorator extends BookDecorator{
    private double qualiCost;

    public QualifyingDecorator(ConcreteBooking booking, double priceModifier) {
        super(booking);
        qualiCost = priceModifier;
    }

    public void setQualiCost(double priceModifier) {qualiCost = priceModifier;}

    @Override
    public double getCost(){
        return super.getCost() + qualiCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = null;
        if(super.getDescription() != null){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Qualifica";
    }
}
