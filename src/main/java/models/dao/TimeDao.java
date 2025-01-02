package models.dao;

import models.TimeSlot;

public abstract class TimeDao {
    public abstract void addSlot(String key, TimeSlot slot);
    public abstract TimeSlot getSlot(String key);
}
