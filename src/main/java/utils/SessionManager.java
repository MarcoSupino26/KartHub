package utils;

import models.user.User;

import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;
    private HashMap<String, BookingSession> bookingSessions;
    private HashMap<String, ManageSession> manageSessions;
    private HashMap<String, EventSession> eventSessions;

    protected SessionManager(){
        bookingSessions = new HashMap<>();
        manageSessions = new HashMap<>();
        eventSessions = new HashMap<>();
    }

    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public User getLoggedUser(){
        return loggedUser;
    }

    public void setLoggedUser(User user){
        this.loggedUser = user;
    }

    public void freeSession(){
        this.loggedUser = null;
    }

    public void createBookingSession(BookingSession bookingSession){
        bookingSessions.put(this.loggedUser.getUsername(), bookingSession);
    }

    public void createManageSession(ManageSession manageSession){
        manageSessions.put(this.loggedUser.getUsername(), manageSession);
    }

    public void freeBookingSession(){
        bookingSessions.remove(loggedUser.getUsername());
    }

    public void freeManageSession(){
        manageSessions.remove(loggedUser.getUsername());
    }

    public BookingSession getBookingSession(){
        return bookingSessions.get(this.loggedUser.getUsername());
    }

    public ManageSession getManageSession(){return manageSessions.get(this.loggedUser.getUsername());}

    public void createEventSession(EventSession eventSession){
        eventSessions.put(this.loggedUser.getUsername(), eventSession);
    }

    public EventSession getEventSession(){
        return eventSessions.get(this.loggedUser.getUsername());
    }

    public void freeEventSession(){
        eventSessions.remove(loggedUser.getUsername());
    }
}
