package models.track;

import java.util.List;

public class TrackDaoFsys extends TrackDao{
    //Non implementato
    @Override
    public void insertTrack(Track track) {
        return;
    }

    @Override
    public Track getTrack(String name) {
        return null;
    }

    @Override
    public List<Track> getAllTracks() {
        return List.of();
    }

    @Override
    public Track getTrackByUser(String username) {
        return null;
    }
}
