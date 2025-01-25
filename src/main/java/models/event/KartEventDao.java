package models.event;

import java.util.List;

public abstract class KartEventDao {
    public abstract List<KartEvent> getEventsByTrack(String track);
    public abstract List<KartEvent> getAllEvents();
    public abstract void addKartEvent(KartEvent kartEvent);
}
