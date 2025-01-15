package utils;

import models.track.Track;

public class BookingSession {
    private Track track;

    public BookingSession(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

}
