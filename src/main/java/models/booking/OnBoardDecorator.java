package models.booking;

public class OnBoardDecorator extends BookDecorator{
    private double onBoardCost;

    public OnBoardDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        onBoardCost = priceModifier;
    }

    public void setPracticeCost(double priceModifier) {onBoardCost = priceModifier;}

    @Override
    public double getCost(){
        return super.getCost() + onBoardCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = "";
        if(!super.getDescription().equals("")){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "OnBoard";
    }
}
