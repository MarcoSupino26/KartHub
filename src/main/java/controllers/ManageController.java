package controllers;

import beans.TrackBean;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import models.user.UserDao;

import java.util.List;

public class ManageController {

    public ManageController() {}

    public boolean registeredTrack() { //controlla se l'owner ha un tracciato associato
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = TrackBean.getInstance().getOwner();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)){
                return true;
            }
        }
        return false;
    }
}
