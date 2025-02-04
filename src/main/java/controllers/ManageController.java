package controllers;

import beans.*;
import exceptions.DataLoadException;
import models.booking.BookingInterface;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;
import models.user.Customer;
import models.user.Owner;
import models.user.User;
import models.user.UserDao;
import utils.session.ManageSession;
import utils.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ManageController {

    public ManageController() {//La new non ha bisogno di un parametro
    }

    public boolean registeredTrack() { //controlla se l'owner ha un tracciato associato
        User owner = SessionManager.getInstance().getLoggedUser();
        Track track = ((Owner)owner).getTrack();
        if(track != null) {
            ManageSession mS = new ManageSession();
            mS.setTrackName(track.getName());
            SessionManager.getInstance().createManageSession(mS);
            return true;
        }else return false;
    }

    public List<BookingsDisplayBean> getBookings() {
        List<BookingsDisplayBean> bookingBeans = new ArrayList<>();
        Track track;
        User owner = SessionManager.getInstance().getLoggedUser();
        track = ((Owner)owner).getTrack();

        List<BookingInterface> bookings = track.allBookings();
        for (BookingInterface booking : bookings) {
            BookingsDisplayBean bookingBean = getBookingsDisplayBean(booking);
            bookingBeans.add(bookingBean);
        }

        return bookingBeans;
    }

    private static BookingsDisplayBean getBookingsDisplayBean(BookingInterface booking) {
        BookingsDisplayBean bookingBean = new BookingsDisplayBean(booking.getSelectedDay());
        bookingBean.setPersonal(String.valueOf(booking.getPersonal()));
        bookingBean.setSelectedDay(booking.getSelectedDay());
        bookingBean.setRental(String.valueOf(booking.getRental()));
        bookingBean.setShift(String.valueOf(booking.getShift()));
        bookingBean.setUser(booking.getUser());
        bookingBean.setCost(String.valueOf(booking.getCost()));
        bookingBean.setDescription(booking.getDescription());
        bookingBean.setSelectedDay(booking.getSelectedDay());
        return bookingBean;
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
        ManageSession manageSession = SessionManager.getInstance().getManageSession();
        manageSession.setAvailableKarts(shifts.getAvailableKarts());
        manageSession.setOpening(shifts.getOpeningHour());
        manageSession.setClosing(shifts.getClosingHour());
        manageSession.setDuration(shifts.getDuration());
    }

    public void saveTrack(CostBean costBean){
        Track newTrack = new Track();
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

        User logged = SessionManager.getInstance().getLoggedUser();
        ((Owner) logged).setTrack(newTrack);
        UserDao userDao = FactoryDAO.getInstance().createUserDao();
        try {
            userDao.addUser(logged.getUsername(), logged);
        }catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        try {
            trackDao.insertTrack(newTrack);
        }catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }

    public TrackProfileBean getTrackInfo(){
        User owner = SessionManager.getInstance().getLoggedUser();
        Track ownedTrack = ((Owner)owner).getTrack();
        ManageSession mS = new ManageSession();
        mS.setTrackName(ownedTrack.getName());
        return new TrackProfileBean(ownedTrack.getImage(), ownedTrack.getName());
    }
}
