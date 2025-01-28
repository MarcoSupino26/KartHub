package controllers;

import beans.EventCreationBean;
import beans.TrackEventBean;
import beans.UserEventsBean;
import exceptions.DataLoadException;
import models.dao.factory.FactoryDAO;
import models.event.KartEvent;
import models.event.KartEventDao;
import models.track.Track;
import models.track.TrackDao;
import models.user.Owner;
import models.user.User;
import models.user.UserDao;
import utils.session.EventSession;
import utils.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    public EventManager() {//La new non ha bisogno di un parametro
    }


    public void startEventSession() {
        User owner = SessionManager.getInstance().getLoggedUser();
        String trackName = "";

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        List<Track> tracks = new ArrayList<>();
        try{
            tracks = trackDao.getAllTracks();
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        for (Track track : tracks) {
            if (track.getOwner().getUsername().equals(owner.getUsername())) {
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
        List<KartEvent> kartEvents = new ArrayList<>();
        try {
            kartEvents = kartEventDao.getAllEvents();
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
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
        Track track;

        User logged = SessionManager.getInstance().getLoggedUser();
        track = ((Owner)logged).getTrack();
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
        Track track;
        User logged = SessionManager.getInstance().getLoggedUser();
        track = ((Owner)logged).getTrack();
        track.addEvent(kartEvent);
        ((Owner) logged).setTrack(track);
        UserDao userDao = FactoryDAO.getInstance().createUserDao();
        try {
            userDao.addUser(logged.getUsername(), logged);
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        try {
            trackDao.insertTrack(track);
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        try{
            kartEventDao.addKartEvent(kartEvent);
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        SessionManager.getInstance().freeEventSession();
    }

    public void setSoldTickets(int soldTickets) {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        eventSession.setShoppedTickets(soldTickets);

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = null;
        try {
            track = trackDao.getTrack(eventSession.getTrackname());
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        List<KartEvent> kartEvents = track.allEvents();

        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        for (KartEvent kartEvent : kartEvents) {
            if(kartEvent.getEventName().equals(eventSession.getCurrentKartEvent().getEventName())){
                kartEvent.setTickets(kartEvent.getTickets() - soldTickets);
                try{
                    kartEventDao.addKartEvent(kartEvent);
                }catch (DataLoadException e){
                    System.out.println(e.getMessage());
                }
                eventSession.setCurrentKartEvent(kartEvent);
            }
        }
        User owner = track.getOwner();
        UserDao userDao = FactoryDAO.getInstance().createUserDao();
        ((Owner)owner).setTrack(track);
        try {
            userDao.addUser(owner.getUsername(), owner);
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
        try{
            trackDao.insertTrack(track);
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
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
