package utils;

import models.user.User;

import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;
    private static HashMap<String, BookingSession> bookingSessions;
    private static HashMap<String, ManageSession> manageSessions;

    protected SessionManager(){
        bookingSessions = new HashMap<>();
        manageSessions = new HashMap<>();
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

    public void createBookingSession(BookingSession bookingSession, String username){
        bookingSessions.put(username, bookingSession);
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

    public BookingSession getBookingSession(String username){
        return bookingSessions.get(username);
    }

    public ManageSession getManageSession(String username){return manageSessions.get(username);}
}
