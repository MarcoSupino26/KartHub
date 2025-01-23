package beans;

import models.slots.TimeSlot;

import java.util.List;

public class CombinedSlotsBean2 {
    private List<SlotBean> generatedSlots;
    private boolean raceChecked;
    private boolean fpChecked;
    private boolean qualiChecked;

    public CombinedSlotsBean2(List<SlotBean> generatedSlots, boolean raceChecked, boolean fpChecked, boolean qualiChecked) {
        this.generatedSlots = generatedSlots;
        this.raceChecked = raceChecked;
        this.fpChecked = fpChecked;
        this.qualiChecked = qualiChecked;
    }

    public List<SlotBean> getGeneratedSlots() {return generatedSlots;}

    public boolean isRaceChecked() {return raceChecked;}

    public boolean isFpChecked() {return fpChecked;}

    public boolean isQualiChecked() {return qualiChecked;}
}
