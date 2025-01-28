import models.track.Track;
import models.slots.TimeSlot;
import org.junit.jupiter.api.Test;
import controllers.BookManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlotGenerationTest {

    @Test
    void testTimeSlotGeneration() {
        double openingHour = 9.0;
        double closingHour = 12.0;
        double shiftDuration = 30;

        Track track = new Track();
        track.setOpeningHour(openingHour);
        track.setClosingHour(closingHour);
        track.setShiftDuration(shiftDuration);

        List<TimeSlot> timeSlots = new BookManager().getTimeSlotList(track);


        assertNotNull(timeSlots, "Time slot list should be not null");
        assertEquals(6, timeSlots.size(), "Slots number should be 6");


        double expectedStart = openingHour;
        for (TimeSlot slot : timeSlots) {
            assertEquals(expectedStart, slot.getStartTime(), 0.01, "Slot start time should be " + expectedStart);
            expectedStart = slot.getEndTime();
        }


        assertEquals(closingHour, timeSlots.get(timeSlots.size() - 1).getEndTime(), 0.01, "Last slot should terminate at " + closingHour);
    }
}

