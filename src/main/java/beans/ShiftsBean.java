package beans;

public class ShiftsBean {
    private final int availableKarts;
    private final double openingHour;
    private final double closingHour;
    private final int duration;

    public ShiftsBean(int karts, double openingHour, double closingHour, int duration) {
        this.availableKarts = karts;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.duration = duration;
    }

    public int getAvailableKarts() {
        return availableKarts;
    }

    public int getDuration() {
        return duration;
    }

    public double getOpeningHour() {
        return openingHour;
    }

    public double getClosingHour() {
        return closingHour;
    }
}
