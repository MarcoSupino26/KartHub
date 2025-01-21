package models.event;

import java.util.List;

public abstract class KartEventDao {
    public abstract KartEvent getKartEvent(String eventName);
    public abstract List<KartEvent> getAllEvents();
    public abstract void addKartEvent(KartEvent kartEvent);
}
