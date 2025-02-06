package models.booking;

public class FreePracticeDecorator extends BookDecorator {
    private final double fpCost;

    public FreePracticeDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        fpCost = priceModifier;
    }

    @Override
    public double getCost(){
        return super.getCost() + fpCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Prova libera";
    }

}
