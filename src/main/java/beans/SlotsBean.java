package beans;

import models.slots.TimeSlot;

import java.util.List;

public class SlotsBean {
    private final List<TimeSlot> timeSlots;

    public SlotsBean(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
