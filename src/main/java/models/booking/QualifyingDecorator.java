package models.booking;

public class QualifyingDecorator extends BookDecorator{
    private final double qualiCost;

    public QualifyingDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        qualiCost = priceModifier;
    }

    @Override
    public double getCost(){
        return super.getCost() + qualiCost;
    }

    @Override
    public String getDescription() {
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Qualifica";
    }
}
