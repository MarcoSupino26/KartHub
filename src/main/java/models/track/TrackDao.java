package models.track;

import java.util.List;

public abstract class TrackDao {
    public abstract void insertTrack(Track track);
    public abstract Track getTrack(String name);
    public abstract List<Track> getAllTracks();
}
