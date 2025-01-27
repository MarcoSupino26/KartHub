package models.user;

import models.track.Track;

public class Owner extends User{
    private Track track;

    public Owner(String usrname, String passw, String type) {
        super(usrname, passw, type);
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }
}
