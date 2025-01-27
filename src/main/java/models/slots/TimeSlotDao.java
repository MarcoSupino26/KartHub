package models.slots;

import java.time.LocalDate;
import java.util.List;

public abstract class TimeSlotDao {
    public abstract List<TimeSlot> getTimeSlots(String trackName, LocalDate date);
    public abstract List<LocalDate> getDatesForTrack(String trackName);
    public abstract void insertTimeSlots(List<TimeSlot> timeSlots, LocalDate day, String trackName);
}
