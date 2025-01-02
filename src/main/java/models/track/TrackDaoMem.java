package models.track;

import java.util.HashMap;

public class TrackDaoMem extends TrackDao {
    private final HashMap<String, Track> tracks;
    private static TrackDaoMem instance;

    protected TrackDaoMem() {
        tracks = new HashMap<>();
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
            System.out.println("Il tracciato" + name + " è già esistente!");
        }
    }

    @Override
    public Track getTrack(String trackName) {
        return tracks.get(trackName);
    }
}
