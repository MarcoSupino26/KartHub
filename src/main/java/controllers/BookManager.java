package controllers;

import beans.DateBean;
import beans.OptionsBean;
import beans.DisplayBean;
import beans.SlotsBean;
import models.booking.*;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;
import models.track.Track;
import models.track.TrackDao;
import utils.BookingSession;
import utils.SessionManager;

import java.sql.Time;
import java.time.LocalDate;
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
    public void setBookingSession(String trackName) {
        List<Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            if (track.getName().equals(trackName)) {
                BookingSession bookingSession = new BookingSession(track);
                String logged = SessionManager.getInstance().getLoggedUser().getUsername();
                SessionManager.getInstance().createBookingSession(bookingSession, logged);
            }
        }
    }
    public void generateSlots(DateBean dateBean){
        BookingSession session = SessionManager.getInstance().getBookingSession(SessionManager.getInstance().getLoggedUser().getUsername());
        Track track = session.getTrack();

        double start = track.getOpeningHour();
        double end = track.getClosingHour();
        double duration = track.getShiftDuration();

        int totalMinutes = (int) ((end - start) * 60);
        int numberofSlots = totalMinutes / (int) duration;

        List <TimeSlot> timeSlots = new ArrayList<TimeSlot>();

        for(int i = 0; i < numberofSlots; i++){
            double startSlot = start + ((duration * i) % 60)/100.0 + (int) (i / (60/duration));
            double endSlot = start + ((duration * (i+1)) % 60)/100.0 + (int) ((i+1) / (60/duration));
            TimeSlot slot = new TimeSlot(startSlot, endSlot, true);
            timeSlots.add(slot);
        }

        LocalDate date = dateBean.getDate();
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        track = trackDao.getTrack(track.getName());
        track.addTimeSlots(timeSlots, date);

        session.getTrack().addTimeSlots(timeSlots, date);
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

        ConcreteBooking concreteBooking1 = (ConcreteBooking) booking;
        concreteBooking1.setRental(options.getRental());
        concreteBooking1.setPersonal(options.getPersonal());
        /*BookingDao bookDao = FactoryDAO.getInstance().createBookingDao();
        bookDao.addBooking(booking);*/
    }
}
