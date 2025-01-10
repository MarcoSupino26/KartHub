package models.slots;


public class TimeSlot{
    private int startTime;
    private int endTime;
    private boolean available;

    public TimeSlot(int startTime, int endTime, boolean available) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }
}
