package controllers;

import beans.InfoBean;
import beans.ShiftsBean;
import beans.TrackBean;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import utils.SessionManager;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.List;

public class ManageController {
    private static ManageController instance;
    private Track newTrack;

    protected ManageController() {}

    public static ManageController getInstance() {
        if (instance == null) {
            instance = new ManageController();
        }
        return instance;
    }

    public boolean registeredTrack(){ //controlla se l'owner ha un tracciato associato
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = SessionManager.getInstance().getLoggedUser();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                return true;
            }
        }
        return false;
        //qui dovrei gestire l'eccezione
    }

    public void saveTrack(TrackBean track) {
        newTrack = new Track();
        newTrack.setName(track.getName());
        newTrack.setAddress(track.getAddress());
        newTrack.setDescription(track.getDescription());
        newTrack.setOwner(SessionManager.getInstance().getLoggedUser());
        newTrack.setImage(track.getImage());
    }

    public void saveShifts(ShiftsBean shifts) {
        newTrack.setAvailableKarts(shifts.getAvailableKarts());
        double start = shifts.getOpeningHour();
        double end = shifts.getClosingHour();
        double duration = shifts.getDuration();

        newTrack.setOpeningHour(start);
        newTrack.setClosingHour(end);
        newTrack.setShiftDuration(duration);

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        trackDao.insertTrack(newTrack);
    }

    public InfoBean getTrackInfo(){
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = SessionManager.getInstance().getLoggedUser();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                return new InfoBean(track.getImage(), track.getName());
            }
        }
        return null;
    }
}
