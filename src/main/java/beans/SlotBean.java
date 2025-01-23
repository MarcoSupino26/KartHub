package beans;

public class SlotBean {
    private final double slotStart;
    private final double slotEnd;
    private final boolean free;

    public SlotBean(double slotStart, double slotEnd, boolean free) {
        this.slotStart = slotStart;
        this.slotEnd = slotEnd;
        this.free = free;
    }

    public double getSlotStart(){return slotStart;}

    public double getSlotEnd(){return slotEnd;}

    public boolean isFree(){return free;}
}
