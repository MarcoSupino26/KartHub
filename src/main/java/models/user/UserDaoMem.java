package models.user;

import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;

import java.util.HashMap;

public class UserDaoMem extends UserDao {
    private final HashMap<String, User> users;
    private static UserDaoMem instance;

    protected UserDaoMem(){
        users = new HashMap<>();
        Owner owner = new Owner("Marco", "psw1", "Proprietario");
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack("Kartodromo Supox");
        track.setOwner(owner);
        trackDao.insertTrack(track);
        owner.setTrack(track);
        users.put("Marco", owner);
        Customer customer = new Customer("Supino", "psw2", "Cliente");
        users.put("Supino", customer);
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
