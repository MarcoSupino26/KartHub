package controllers;

import beans.OptionsBean;
import beans.DisplayBean;
import models.booking.*;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import utils.BookingSession;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class BookManager {

    public List<DisplayBean> getTracks(){
        List<DisplayBean> disBeans = new ArrayList<DisplayBean>();
        List<Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            DisplayBean displayBean = new DisplayBean();
            displayBean.setImage(track.getImage());
            displayBean.setName(track.getName());
            displayBean.setDescription(track.getDescription());
            disBeans.add(displayBean);
        }
        return disBeans;

    }
    public void setBookingSession(String trackName){
        List <Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            if(track.getName().equals(trackName)){
                BookingSession bookingSession = new BookingSession(track);
                String logged = SessionManager.getInstance().getLoggedUser().getUsername();
                SessionManager.getInstance().createBookingSession(bookingSession, logged);
            }
        }
    }

    public void saveBooking(OptionsBean options){
        BookingInterface booking = new ConcreteBooking();
        String logged = SessionManager.getInstance().getLoggedUser().getUsername();
        BookingSession bookingSession = SessionManager.getInstance().getBookingSession(logged);
        Track track = bookingSession.getTrack();

        ConcreteBooking concreteBooking = (ConcreteBooking) booking;
        concreteBooking.setRental(options.getRental());
        concreteBooking.setPersonal(options.getPersonal());

        if(options.isRace()){
            booking = new RaceDecorator(booking, track.getCost(1));
        }

        if(options.isFp()){
            booking = new ChampagneDecorator(booking, track.getCost(2));
        }

        if(options.isQuali()){
            booking = new QualifyingDecorator(booking, track.getCost(3));
        }

        if(options.isChampagne()){
            booking = new ChampagneDecorator(booking, track.getCost(4));
        }

        if(options.isMedals()){
            booking = new MedalsDecorator(booking, track.getCost(5));
        }

        if(options.isOnBoard()){
            booking = new OnBoardDecorator(booking, track.getCost(6));
        }

        BookingDao bookDao = FactoryDAO.getInstance().createBookingDao();
        bookDao.addBooking(booking);
    }
}
