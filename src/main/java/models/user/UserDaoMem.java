package models.user;

import java.util.HashMap;

public class UserDaoMem extends UserDao {
    private final HashMap<String, User> users;
    private static UserDaoMem instance;

    protected UserDaoMem(){
        users = new HashMap<>();
        users.put("Marco", new Owner("Marco", "psw1", "Proprietario"));
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

    @Override
    public User getUserByUsername(String username) {
        return null;
    }
}
