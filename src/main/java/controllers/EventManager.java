package controllers;

import beans.EventBean;
import models.dao.factory.FactoryDAO;
import models.event.KartEvent;
import models.event.KartEventDao;
import models.track.Track;
import models.track.TrackDao;
import utils.EventSession;
import utils.SessionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

    public EventManager() {}

    public void getAllEvents() {

        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        List<KartEvent> kartEvents = kartEventDao.getAllEvents();

        EventSession eventSession = new EventSession();
        eventSession.setKartEvents(kartEvents);

        SessionManager.getInstance().createEventSession(eventSession);
    }

    public List<EventBean> getAssociatedEvents(String trackName){
        List<EventBean> eventBeans = new ArrayList<>();

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack(trackName);


        List<KartEvent> kartEventList = track.allEvents();
        for(KartEvent kartEvent : kartEventList){
            String eventName = kartEvent.getEventName();
            int tickets = kartEvent.getTickets();
            LocalDate date = kartEvent.getEventDate();
            LocalTime time = kartEvent.getEventTime();
            EventBean eventBean = new EventBean(eventName, tickets, date, time);
            eventBeans.add(eventBean);
        }
        return eventBeans;
    }
}
