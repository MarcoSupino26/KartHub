package models.dao;

import models.TimeSlot;

import java.util.HashMap;

public class TimeDaoMem extends TimeDao {
    private final HashMap<String, TimeSlot> slots;
    private static TimeDaoMem instance;

    protected TimeDaoMem() {
        slots = new HashMap<>();
    }

    public static TimeDaoMem getInstance() {
        if (instance == null) {
            instance = new TimeDaoMem();
        }
        return instance;
    }

    @Override
    public void addSlot(String key, TimeSlot timeSlot){
        if(!slots.containsKey(key)){
            slots.put(key, timeSlot);
        }else {
            System.out.println("La fascia " + key + " è già esistente!");
        }
    }

    @Override
    public TimeSlot getSlot(String key){
        return slots.get(key);
    }

}
