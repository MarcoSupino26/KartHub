package models.booking;

public class MedalsDecorator extends BookDecorator {
    private double medalsCost;

    public MedalsDecorator(BookingInterface booking, double priceModifier) {
        super(booking);
        medalsCost = priceModifier;
    }

    public void setMedalsCost(double priceModifier) {medalsCost = priceModifier;}

    @Override
    public double getCost(){
        return super.getCost() + medalsCost;
    }

    @Override
    public String getDescription(){
        String addedFormat = "";
        if(!super.getDescription().isEmpty()){
            addedFormat = " + ";
        }
        return super.getDescription() + addedFormat + "Premiazione Medaglia";
    }
}
