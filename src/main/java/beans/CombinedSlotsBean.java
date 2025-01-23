package beans;

import java.util.List;

public class CombinedSlotsBean {
    private final List<SlotBean> generatedSlots;
    private final boolean raceChecked;
    private final boolean fpChecked;
    private final boolean qualiChecked;

    public CombinedSlotsBean(List<SlotBean> generatedSlots, boolean raceChecked, boolean fpChecked, boolean qualiChecked) {
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
