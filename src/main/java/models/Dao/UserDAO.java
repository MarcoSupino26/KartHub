package models.Dao;

import models.User;

import java.util.HashMap;

public class UserDAO {

    private static UserDAO instance; //istanza privata unica
    private HashMap<String, User> users; //hash map per mantenere gli utenti in memoria

    private UserDAO(){
        users = new HashMap<>(); //inizializza hashmap
    }

    public static UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    //inserimento utente
    public void addUser(String username, User user){
        if(!users.containsKey(username)){
            users.put(username, user);
        }else {
            System.out.println("L'utente " + username + " è già esistente!");
        }
    }

    public User getUser(String username, String password){
        if(users.containsKey(username) && users.get(username).getPassword().equals(password)){
            return users.get(username);
        } else {
            return null;
        }
    }

    //removeUser()

    //updateUser()
}
