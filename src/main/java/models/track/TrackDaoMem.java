package models.track;

import javafx.scene.image.Image;
import models.dao.factory.FactoryDAO;
import models.user.Owner;
import models.user.User;
import models.user.UserDao;

import java.util.*;

public class TrackDaoMem extends TrackDao {
    private final HashMap<String, Track> tracks;
    private static TrackDaoMem instance;

    protected TrackDaoMem() {
        tracks = new HashMap<>();

        Map<String, Object> trackDetails = new HashMap<>();
        trackDetails.put("name", "Kartodromo Supox");
        trackDetails.put("description", "Prezzi stracciati e divertimento assicurato vi aspettano nel nostro tracciato!");
        trackDetails.put("address", "Via dei laghi, 2");
        trackDetails.put("availableKarts", 14);
        trackDetails.put("image", new Image("file:C:\\Users\\supin\\Desktop\\Università\\ISPW\\Interfacce\\tt racing.jpg"));
        trackDetails.put("owner", new Owner("demo", "password", "Proprietario"));
        double[] timings = {10.00, 18.00, 15.00};
        Track demoTrack = saveDemoTrack(new Track(), trackDetails, timings);
        tracks.put(demoTrack.getName(), demoTrack);
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
        demo.setCost(new ArrayList<>(Collections.nCopies(6, 10.00)));
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
        tracks.put(track.getName(), track);
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
    public Track getTrackByUser(String username) {
        return null;
    }

}
