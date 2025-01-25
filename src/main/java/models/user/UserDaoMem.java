package models.user;

import java.util.HashMap;

public class UserDaoMem extends UserDao {
    private final HashMap<String, User> users; //hash map per mantenere gli utenti in memoria
    private static UserDaoMem instance;

    protected UserDaoMem(){
        users = new HashMap<>();
        String own = "Proprietario";
        users.put("Marco", new User("Marco", "psw1", own));
        users.put("Emanuele", new User("Emanuele", "psw2", own));
        users.put("Simone", new User("Simone", "psw3", own));
        users.put("Edoardo", new User("Edoardo", "psw4", "Cliente"));
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


    //rivedere la logica del null, forse meglio metterla nel controllore

    //removeUser()

    //updateUser()
}
