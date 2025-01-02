package models.dao;

import models.User;

import java.util.HashMap;

public class UserDaoMem extends UserDao{
    private final HashMap<String, User> users; //hash map per mantenere gli utenti in memoria
    private static UserDaoMem instance;

    protected UserDaoMem(){
        users = new HashMap<>();
    }


    public static UserDaoMem getInstance(){
        if(instance == null){
            instance = new UserDaoMem();
        }
        return instance;
    }

    //inserimento utente
    @Override
    public void addUser(String username, User user){
        if(!users.containsKey(username)){
            users.put(username, user);
        }else {
            System.out.println("L'utente " + username + " è già esistente!");
        }
    }

    @Override
    public User getUser(String username, String password){
        if(users.containsKey(username) && users.get(username).getPassword().equals(password)){
            return users.get(username);
        } else {
            return null;
        }
    }
    //rivedere la logica del null, forse meglio metterla nel controllore

    //removeUser()

    //updateUser()
}
