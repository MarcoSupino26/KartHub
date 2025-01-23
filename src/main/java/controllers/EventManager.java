package controllers;

import beans.EventCreationBean;
import beans.TrackEventBean;
import beans.UserEventsBean;
import models.dao.factory.FactoryDAO;
import models.event.KartEvent;
import models.event.KartEventDao;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import utils.EventSession;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    public EventManager() {//La new non ha bisogno di un parametro
    }


    public void startEventSession() {
        User owner = SessionManager.getInstance().getLoggedUser();
        String trackName = "";

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                trackName = track.getName();
            }
        }

        EventSession eventSession = new EventSession();
        eventSession.setTrackname(trackName);
        SessionManager.getInstance().createEventSession(eventSession);
    }

    public void clearSession() {
        SessionManager.getInstance().freeEventSession();
    }

    public List<UserEventsBean> getAllEvents() {
        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        List<KartEvent> kartEvents = kartEventDao.getAllEvents();
        List<UserEventsBean> userEventsBeans = new ArrayList<>();

        for(KartEvent kartEvent : kartEvents){
            UserEventsBean eventBean = new UserEventsBean();
            eventBean.setEvent(kartEvent.getEventName());
            eventBean.setType(kartEvent.getEventType());
            eventBean.setPlace(kartEvent.getTrackName());
            eventBean.setEventDay(kartEvent.getEventDate());
            eventBean.setEventStart(kartEvent.getEventTime());
            eventBean.setRemainingTickets(kartEvent.getTickets());
            eventBean.setTicketPrice(kartEvent.getCost());
            userEventsBeans.add(eventBean);
        }

        EventSession eventSession = new EventSession();
        eventSession.setKartEvents(kartEvents);

        SessionManager.getInstance().createEventSession(eventSession);
        return userEventsBeans;
    }

    public List<TrackEventBean> getAssociatedEvents() {
        List<TrackEventBean> beans = new ArrayList<>();
        String trackName = SessionManager.getInstance().getEventSession().getTrackname();

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack(trackName);

        List<KartEvent> kartEventList = track.allEvents();
        for (KartEvent kartEvent : kartEventList) {
            TrackEventBean bean = new TrackEventBean();
            bean.setTrackEventName(kartEvent.getEventName());
            bean.setEventTickets(kartEvent.getTickets());
            bean.setDay(kartEvent.getEventDate());
            bean.setStartHour(kartEvent.getEventTime());
            beans.add(bean);
        }
        return beans;
    }

    public void setCurrentEvent(UserEventsBean currentEvent) {
        SessionManager.getInstance().getEventSession().setTrackname(currentEvent.getPlace());
        KartEvent currEvent = new KartEvent(currentEvent.getEvent());
        currEvent.setEventType(currentEvent.getType());
        currEvent.setTrackName(currentEvent.getPlace());
        currEvent.setEventDate(currentEvent.getEventDay());
        currEvent.setEventTime(currentEvent.getEventStart());
        currEvent.setCost(currentEvent.getTicketPrice());
        currEvent.setTickets(currentEvent.getRemainingTickets());
        SessionManager.getInstance().getEventSession().setCurrentKartEvent(currEvent);
    }

    public void saveEvent(EventCreationBean eventBean) {
        String trackName = SessionManager.getInstance().getManageSession().getTrackName();

        KartEvent kartEvent = new KartEvent(eventBean.getEventName());
        kartEvent.setTickets(eventBean.getAvailableTickets());
        kartEvent.setEventDate(eventBean.getDate());
        kartEvent.setEventTime(eventBean.getTime());
        kartEvent.setCost(eventBean.getPrice());
        kartEvent.setTrackName(trackName);
        kartEvent.setEventType(eventBean.getType());

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack(trackName);
        track.addEvent(kartEvent);
        trackDao.updateTrack(track);

        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        kartEventDao.addKartEvent(kartEvent);

        SessionManager.getInstance().freeEventSession();
    }

    public void setSoldTickets(int soldTickets) {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        eventSession.setShoppedTickets(soldTickets);

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack(eventSession.getTrackname());
        List<KartEvent> kartEvents = track.allEvents();

        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        for (KartEvent kartEvent : kartEvents) {
            if(kartEvent.getEventName().equals(eventSession.getCurrentKartEvent().getEventName())){
                kartEvent.setTickets(kartEvent.getTickets() - soldTickets);
                kartEventDao.addKartEvent(kartEvent);
                eventSession.setCurrentKartEvent(kartEvent);
            }
        }
        trackDao.updateTrack(track);
    }

    public int getSoldTickets() {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        return eventSession.getShoppedTickets();
    }

    public double getTicketCost(){
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        return eventSession.getCurrentKartEvent().getCost();
    }

    public EventCreationBean getPaymentInfo() {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        EventCreationBean bean = new EventCreationBean(eventSession.getCurrentKartEvent().getEventName());
        bean.setType(eventSession.getCurrentKartEvent().getEventType());
        bean.setPrice(eventSession.getCurrentKartEvent().getCost());
        bean.setDate(eventSession.getCurrentKartEvent().getEventDate());
        bean.setTime(eventSession.getCurrentKartEvent().getEventTime());
        bean.setAvailableTickets(eventSession.getCurrentKartEvent().getTickets());
        bean.setTrack(eventSession.getTrackname());
        return bean;
    }
}
