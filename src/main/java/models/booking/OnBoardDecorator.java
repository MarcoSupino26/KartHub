package models.booking;

public class OnBoardDecorator extends BookDecorator{
    private final double onBoardCost;

    public OnBoardDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        onBoardCost = priceModifier;
    }

    @Override
    public double getCost(){
        return super.getCost() + onBoardCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "OnBoard";
    }
}
