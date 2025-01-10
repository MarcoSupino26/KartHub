package controllers;

import beans.InfoBean;
import beans.ShiftsBean;
import beans.TrackBean;
import javafx.scene.Scene;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import models.user.UserDao;
import utils.Session;
import views.SceneManager;
import views.TrackManager;

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

    public void registeredTrack() { //controlla se l'owner ha un tracciato associato
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = Session.getInstance().getLoggedUser();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                InfoBean trackInfo = new InfoBean(track);
                SceneManager.changeScene("/trackmanager.fxml");
                TrackManager manager = SceneManager.getController("/trackmanager.fxml");
                manager.setData(trackInfo);
                SceneManager.showScene();
            }
        }
        //qui dovrei gestire l'eccezione
    }

    public void saveTrack(TrackBean track) {
        newTrack = new Track();
        newTrack.setName(track.getName());
        newTrack.setAddress(track.getAddress());
        newTrack.setDescription(track.getDescription());
        newTrack.setOwner(Session.getInstance().getLoggedUser());
        newTrack.setImage(track.getImage());
    }

    public void saveShifts(ShiftsBean shifts) {
        newTrack.setAvailableKarts(shifts.getAvailableKarts());
        int start = shifts.getOpeningHour();
        int end = shifts.getClosingHour();
        int numberOfSlots = (60/shifts.getDuration()) * (start - end);
        List <TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        for (int i = 0; i < numberOfSlots; i++) {
            TimeSlot slot = new TimeSlot(start, end, true);
            timeSlots.add(slot);
        }
        newTrack.setTimeSlots(timeSlots);
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        trackDao.insertTrack(newTrack);
    }

    /*public void getTrack(){
        TrackDao TrackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = Session.getInstance().getLoggedUser();
        List<Track> tracks = TrackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                TrackBean bean = new TrackBean();
            }
        }
    }*/
}
