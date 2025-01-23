package beans;

import models.slots.TimeSlot;

import java.util.List;

public class CombineSlotsBean {
    private List<TimeSlot> generatedSlots;
    private boolean raceChecked;
    private boolean fpChecked;
    private boolean qualiChecked;

    public CombineSlotsBean(List<TimeSlot> generatedSlots, boolean raceChecked, boolean fpChecked, boolean qualiChecked) {
        this.generatedSlots = generatedSlots;
        this.raceChecked = raceChecked;
        this.fpChecked = fpChecked;
        this.qualiChecked = qualiChecked;
    }

    public List<TimeSlot> getGeneratedSlots() {return generatedSlots;}

    public boolean isRaceChecked() {return raceChecked;}

    public boolean isFpChecked() {return fpChecked;}

    public boolean isQualiChecked() {return qualiChecked;}
}
