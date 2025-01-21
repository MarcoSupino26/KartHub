package models.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KartEventDaoMem extends KartEventDao {
    private static KartEventDaoMem instance;
    private static HashMap<String, KartEvent> kartEvents;

    protected KartEventDaoMem() {
        kartEvents = new HashMap<>();
    }

    public static KartEventDaoMem getInstance() {
        if (instance == null) {
            instance = new KartEventDaoMem();
        }
        return instance;
    }

    @Override
    public void addKartEvent(KartEvent kartEvent) {
        kartEvents.put(kartEvent.getEventName(), kartEvent);
    }

    @Override
    public KartEvent getKartEvent(String eventName) {
        if (kartEvents.containsKey(eventName)) {
            return  kartEvents.get(eventName);
        }else{
            return null;
        }
    }

    @Override
    public List<KartEvent> getAllEvents() {
        List <KartEvent> kartEventList = new ArrayList<>();
        kartEventList.addAll(kartEvents.values());
        return kartEventList;
    }
}
