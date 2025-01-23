package controllers;

import beans.*;
import models.booking.BookingInterface;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;
import models.user.User;
import utils.ManageSession;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ManageController {

    public ManageController() {}

    /*public void startManageSession(String trackName){
        ManageSession manageSession = new ManageSession();
        manageSession.setTrackName(trackName);
        SessionManager.getInstance().createManageSession(manageSession);
    }*/

    public boolean registeredTrack() { //controlla se l'owner ha un tracciato associato
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = SessionManager.getInstance().getLoggedUser();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                ManageSession mS = new ManageSession();
                mS.setTrackName(track.getName());
                SessionManager.getInstance().createManageSession(mS);
                return true;
            }
        }
        return false;
    }

    public List<BookingsDisplayBean> getBookings() {
        List<BookingsDisplayBean> bookingBeans = new ArrayList<>();
        String trackName = SessionManager.getInstance().getManageSession().getTrackName();
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        Track track = trackDao.getTrack(trackName);

        List<BookingInterface> bookings = track.allBookings();
        for (BookingInterface booking : bookings) {
            BookingsDisplayBean bookingBean = new BookingsDisplayBean(booking.getSelectedDay());
            bookingBean.setPersonal(String.valueOf(booking.getPersonal()));
            bookingBean.setRental(String.valueOf(booking.getRental()));
            bookingBean.setShift(String.valueOf(booking.getShift()));
            bookingBean.setUser(booking.getUser().getUsername());
            bookingBean.setCost(String.valueOf(booking.getCost()));
            bookingBean.setDescription(booking.getDescription());
            bookingBeans.add(bookingBean);
        }

        return bookingBeans;
    }

    public void createTrack(TrackBean track) {
        ManageSession manageSession = new ManageSession();
        manageSession.setTrackName(track.getName());
        manageSession.setAddress(track.getAddress());
        manageSession.setDescription(track.getDescription());
        manageSession.setOwner(SessionManager.getInstance().getLoggedUser());
        manageSession.setImage(track.getImage());
        SessionManager.getInstance().createManageSession(manageSession);
    }

    public void saveShifts(ShiftsBean shifts) {
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        ManageSession manageSession = SessionManager.getInstance().getManageSession();
        manageSession.setAvailableKarts(shifts.getAvailableKarts());
        manageSession.setOpening(shifts.getOpeningHour());
        manageSession.setClosing(shifts.getClosingHour());
        manageSession.setDuration(shifts.getDuration());
    }

    public void saveTrack(CostBean costBean){
        Track newTrack = new Track();
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        ManageSession ms = SessionManager.getInstance().getManageSession();
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

    public TrackProfileBean getTrackInfo(){
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        User owner = SessionManager.getInstance().getLoggedUser();
        List<Track> tracks = trackDao.getAllTracks();
        for (Track track : tracks) {
            if (track.getOwner().equals(owner)) {
                ManageSession mS = new ManageSession();
                mS.setTrackName(track.getName());
                return new TrackProfileBean(track.getImage(), track.getName());
            }
        }
        return null;
    }
}
