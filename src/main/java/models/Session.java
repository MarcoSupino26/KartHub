package models;

public class Session {
    private static Session instance;
    private User loggedUser;

    protected Session(){}

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
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
