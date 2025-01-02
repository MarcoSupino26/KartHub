package models.track;

public abstract class TrackDao {
    public abstract void insertTrack(Track track);
    public abstract Track getTrack(String name);
}
