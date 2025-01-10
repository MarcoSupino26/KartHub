package beans;

public class ShiftsBean {
    private final int availableKarts;
    private final int openingHour;
    private final int closingHour;
    private final int duration;

    public ShiftsBean(int karts, int openingHour, int closingHour, int duration) {
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

    public int getOpeningHour() {
        return openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }
}
