package models.track;

import javafx.scene.image.Image;
import models.dao.factory.FactoryDAO;
import models.user.User;

import java.util.*;

public class TrackDaoMem extends TrackDao {
    private final HashMap<String, Track> tracks;
    private static TrackDaoMem instance;

    protected TrackDaoMem() {
        tracks = new HashMap<>();

        // Dati per il primo tracciato
        Map<String, Object> trackDetails1 = new HashMap<>();
        trackDetails1.put("name", "Kartodromo Supox");
        trackDetails1.put("description", "Prezzi stracciati e divertimento assicurato vi aspettano nel nostro tracciato!");
        trackDetails1.put("address", "Via dei laghi, 2");
        trackDetails1.put("availableKarts", 14);
        trackDetails1.put("image", new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\tt racing.jpg"));
        trackDetails1.put("owner", FactoryDAO.getInstance().createUserDao().getUser("Marco", "psw1"));

        double[] timings1 = {10.00, 18.00, 15.00};
        Track demoTrack1 = saveDemoTrack(new Track(), trackDetails1, timings1);
        demoTrack1.setCost(new ArrayList<>(Collections.nCopies(6, 10.00)));
        tracks.put(demoTrack1.getName(), demoTrack1);

        // Dati per il secondo tracciato
        Map<String, Object> trackDetails2 = new HashMap<>();
        trackDetails2.put("name", "Kartodromo da Leleh");
        trackDetails2.put("description", "Un pomeriggio all'insegna del divertimento e in mezzo alla natura vi attendono!");
        trackDetails2.put("address", "Via di Palestrina, 21");
        trackDetails2.put("availableKarts", 12);
        trackDetails2.put("image", new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\artena.jpg"));
        trackDetails2.put("owner", FactoryDAO.getInstance().createUserDao().getUser("Emanuele", "psw2"));

        double[] timings2 = {11.00, 18.00, 20.00};
        Track demoTrack2 = saveDemoTrack(new Track(), trackDetails2, timings2);
        demoTrack2.setCost(new ArrayList<>(Collections.nCopies(6, 15.00)));
        tracks.put(demoTrack2.getName(), demoTrack2);

        // Dati per il terzo tracciato
        Map<String, Object> trackDetails3 = new HashMap<>();
        trackDetails3.put("name", "Simon's Track");
        trackDetails3.put("description", "Che fate? Non venite a divertirvi al nostro kartodromo? Beh, vi perdete molto!");
        trackDetails3.put("address", "Via Rocca di Papa, 14");
        trackDetails3.put("availableKarts", 15);
        trackDetails3.put("image", new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\holykartroma.jpg"));
        trackDetails3.put("owner", FactoryDAO.getInstance().createUserDao().getUser("Simone", "psw3"));

        double[] timings3 = {12.00, 17.00, 10.00};
        Track demoTrack3 = saveDemoTrack(new Track(), trackDetails3, timings3);
        demoTrack3.setCost(new ArrayList<>(Collections.nCopies(6, 20.00)));
        tracks.put(demoTrack3.getName(), demoTrack3);
    }

    protected Track saveDemoTrack(Track demo, Map<String, Object> trackDetails, double[] timings) {
        demo.setName((String) trackDetails.get("name"));
        demo.setDescription((String) trackDetails.get("description"));
        demo.setAddress((String) trackDetails.get("address"));
        demo.setAvailableKarts((int) trackDetails.get("availableKarts"));
        demo.setImage((Image) trackDetails.get("image"));
        demo.setOwner((User) trackDetails.get("owner"));
        demo.setOpeningHour(timings[0]);
        demo.setClosingHour(timings[1]);
        demo.setShiftDuration(timings[2]);
        return demo;
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
