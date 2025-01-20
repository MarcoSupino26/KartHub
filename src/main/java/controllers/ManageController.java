package controllers;

import beans.CostBean;
import beans.InfoBean;
import beans.ShiftsBean;
import beans.TrackBean;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import utils.ManageSession;
import utils.SessionManager;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.List;

public class ManageController {
    private static ManageController instance;

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

    public void createTrack(TrackBean track) {
        ManageSession manageSession = new ManageSession();
        manageSession.setTrackName(track.getName());
        manageSession.setAddress(track.getAddress());
        manageSession.setDescription(track.getDescription());
        manageSession.setOwner(SessionManager.getInstance().getLoggedUser());
        manageSession.setImage(track.getImage());
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        SessionManager.getInstance().createManageSession(manageSession,usr);
    }

    public void saveShifts(ShiftsBean shifts) {
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        ManageSession manageSession = SessionManager.getInstance().getManageSession(usr);
        manageSession.setAvailableKarts(shifts.getAvailableKarts());
        manageSession.setOpening(shifts.getOpeningHour());
        manageSession.setClosing(shifts.getClosingHour());
        manageSession.setDuration(shifts.getDuration());
    }

    public void saveTrack(CostBean costBean){
        Track newTrack = new Track();
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        ManageSession ms = SessionManager.getInstance().getManageSession(usr);
        newTrack.setOwner(ms.getOwner());
        newTrack.setShiftDuration(ms.getDuration());
        newTrack.setOpeningHour(ms.getOpening());
        newTrack.setClosingHour(ms.getClosing());
        newTrack.setDescription(ms.getDescription());
        newTrack.setName(ms.getTrackName());
        newTrack.setImage(ms.getImage());
        newTrack.setAvailableKarts(ms.getAvailableKarts());
        newTrack.setCost(costBean.getCost());
        newTrack.setAddress(ms.getAddress());

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
