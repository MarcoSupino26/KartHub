package models.track;

import javafx.scene.image.Image;
import models.dao.factory.FactoryDAO;
import models.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackDaoMem extends TrackDao {
    private final HashMap<String, Track> tracks;
    private static TrackDaoMem instance;

    protected TrackDaoMem() {
        tracks = new HashMap<>();
        double opHour = 10.00;
        double clHour = 18.00;
        double duration = 15.00;

        Track demoTrack1 = new Track();
        String desc1 = "Prezzi stracciati e divertimento assicurato vi aspettano nel nostro tracciato!";
        Image img1 = new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\tt racing.jpg");
        User owner1 = FactoryDAO.getInstance().createUserDao().getUser("Marco", "psw1");
        String name = "Kartodromo Supox";
        demoTrack1 = saveDemoTrack(demoTrack1, name, desc1, "Via dei laghi, 2", 14, img1, owner1, opHour, clHour, duration);
        List<Double> costs1 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            costs1.add(10.00);
        }
        demoTrack1.setCost(costs1);
        tracks.put(demoTrack1.getName(), demoTrack1);

        Track demoTrack2 = new Track();
        String desc2 = "Un pomeriggio all'insegna del divertimento e in mezzo alla natura vi attendono!";
        Image img2 = new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\artena.jpg");
        User owner2 = FactoryDAO.getInstance().createUserDao().getUser("Emanuele", "psw2");
        String name2 = "Kartodromo da Leleh";
        opHour = 11.00;
        duration = 20.00;
        demoTrack2 = saveDemoTrack(demoTrack2, name2, desc2, "Via di Palestrina, 21", 12, img2, owner2, opHour, clHour, duration);
        List<Double> costs2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            costs2.add(15.00);
        }
        demoTrack2.setCost(costs2);
        tracks.put(demoTrack2.getName(), demoTrack2);

        Track demoTrack3 = new Track();
        String desc3 = "Che fate? Non venite a divertirvi al nostro kartodromo? Beh, vi perdete molto!";
        Image img3 = new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\holykartroma.jpg\"");
        User owner3 = FactoryDAO.getInstance().createUserDao().getUser("Simone", "psw3");
        String name3 = "Simon's Track";
        opHour = 12.00;
        clHour = 17.00;
        duration = 10.00;
        demoTrack3 = saveDemoTrack(demoTrack3, name3, desc3, "Via Rocca di Papa, 14", 15, img3, owner3, opHour, clHour, duration);
        List<Double> costs3 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            costs3.add(20.00);
        }
        demoTrack3.setCost(costs3);
        tracks.put(demoTrack3.getName(), demoTrack3);
    }

    protected Track saveDemoTrack(Track demo, String name, String desc, String addr, int avKarts, Image image, User owner, double opHour, double clHour, double duration){
        demo.setName(name);
        demo.setDescription(desc);
        demo.setAddress(addr);
        demo.setAvailableKarts(avKarts);
        demo.setImage(image);
        demo.setOwner(owner);
        demo.setOpeningHour(opHour);
        demo.setClosingHour(clHour);
        demo.setShiftDuration(duration);
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
