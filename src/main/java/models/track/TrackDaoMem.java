package models.track;

import javafx.scene.image.Image;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackDaoMem extends TrackDao {
    private HashMap<String, Track> tracks;
    private static TrackDaoMem instance;

    protected TrackDaoMem() {
        tracks = new HashMap<>();
        Track track = new Track();
        track.setName("Supox autodrome");
        track.setDescription("Venite a divertirvi da noi!");
        track.setAddress("Via frattocchi 2");
        track.setAvailableKarts(11);
        List<TimeSlot> timeSlots = null;
        Image image = new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\tt racing.jpg");
        track.setImage(image);
        track.setOwner(FactoryDAO.getInstance().createUserDao().getUser("marco", "ciao"));
        tracks.put("Supox autodrome", track);
    }

    public static TrackDaoMem getInstance() {
        if (instance == null) {
            instance = new TrackDaoMem();
        }
        return instance;
    }

    @Override
    public void insertTrack(Track track) {
        String name = track.getName();
        if(!tracks.containsKey(name)){
            tracks.put(track.getName(), track);
        }else {
            System.out.println("Il tracciato " + name + " è già esistente!");
        }
    }

    @Override
    public Track getTrack(String trackName) {
        return tracks.get(trackName);
    }

    @Override
    public List<Track> getAllTracks() {
        return new ArrayList<>(tracks.values());
    }

    @Override
    public void updateTrack(Track track){
        tracks.put(track.getName(), track);
    }
}
