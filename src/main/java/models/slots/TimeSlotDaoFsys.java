package models.slots;

import java.time.LocalDate;
import java.util.List;

public class TimeSlotDaoFsys extends TimeSlotDao {
    //Non implementato
    @Override
    public List<TimeSlot> getTimeSlots(String trackName, LocalDate date) {
        return List.of();
    }

    @Override
    public List<LocalDate> getDatesForTrack(String trackName) {
        return List.of();
    }

    @Override
    public void insertTimeSlots(List<TimeSlot> timeSlots, LocalDate day, String trackName) {
        //Non implementato
    }
}
