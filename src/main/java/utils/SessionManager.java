package utils;

import models.user.User;

import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;
    private static HashMap<String, BookingSession> bookingSessions;

    protected SessionManager(){
        bookingSessions = new HashMap<>();
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

    public BookingSession getBookingSession(String username){
        return bookingSessions.get(username);
    }
}