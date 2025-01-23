package models.booking;

public class QualifyingDecorator extends BookDecorator{
    private double qualiCost;

    public QualifyingDecorator(BookingInterface booking, double priceModifier) {
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
        String addedFormat = "";
        if(!super.getDescription().equals("")){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Qualifica";
    }
}
