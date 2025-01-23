package models.slots;

public class TimeSlot{
    private double startTime;
    private double endTime;
    private boolean available;

    public TimeSlot(double startTime, double endTime, boolean available) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }
}
