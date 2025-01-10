package beans;

import models.track.Track;
import models.user.User;

public class InfoBean {
    private Track track;

    public InfoBean(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }
}
