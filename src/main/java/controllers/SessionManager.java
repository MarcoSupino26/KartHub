package controllers;

import models.User;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;
    private SessionManager(){}

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
}
