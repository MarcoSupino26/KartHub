package models.slots;

public abstract class TimeDao {
    public abstract void addSlot(String key, TimeSlot slot);
    public abstract TimeSlot getSlot(String key);
}
