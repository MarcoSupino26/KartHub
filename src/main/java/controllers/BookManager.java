package controllers;

import beans.*;
import javafx.scene.image.Image;
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

    public boolean isSessionOpen(){
        if(SessionManager.getInstance().getBookingSession()!=null) return true;
        else return false;
    }

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

    public TrackProfileBean getSelectedTrack(){
        BookingSession bookingSession = SessionManager.getInstance().getBookingSession();
        String selectedTrack = bookingSession.getTrack().getName();
        Image trackPic = bookingSession.getTrack().getImage();
        return new TrackProfileBean(trackPic, selectedTrack);
    }

    public void setBookingSession(String trackName) {
        List<Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            if (track.getName().equals(trackName)) {
                BookingSession bookingSession = new BookingSession(track);
                String logged = SessionManager.getInstance().getLoggedUser().getUsername();
                SessionManager.getInstance().createBookingSession(bookingSession);
            }
        }
    }
    public void generateSlots(DateBean dateBean){
        BookingSession session = SessionManager.getInstance().getBookingSession();
        Track track = session.getTrack();
        LocalDate date = dateBean.getDate();

        if(session.getTrack().getTimeSlots(date) != null) return;

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

        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        track = trackDao.getTrack(track.getName());
        track.addTimeSlots(timeSlots, date);

        session.getTrack().addTimeSlots(timeSlots, date);
    }

    public SlotsBean getSlots(LocalDate day){
        BookingSession session = SessionManager.getInstance().getBookingSession();
        SlotsBean bean = new SlotsBean(session.getTrack().getTimeSlots(day));
        return bean;
    }

    public SlotsBean getCombinedSlots(CombineSlotsBean bean){
        int requiredSlots = 0;
        List<TimeSlot> generatedSlots = bean.getGeneratedSlots();
        List<TimeSlot> combinedSlots = new ArrayList<>();
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();

        if(bean.isRaceChecked()) requiredSlots += 2;
        if(bean.isQualiChecked()) requiredSlots += 1;
        if(bean.isFpChecked()) requiredSlots += 1;

        SessionManager.getInstance().getBookingSession().setBookedSlots(requiredSlots);

        for (int i = 0; i <= generatedSlots.size() - requiredSlots; i++) {
            boolean allAvailable = true;
            for (int j = 0; j < requiredSlots; j++) {
                if (!generatedSlots.get(i + j).isAvailable()){
                    allAvailable = false;
                    break;
                }
            }
            if (allAvailable){
                TimeSlot start = generatedSlots.get(i);
                TimeSlot end = generatedSlots.get(i + requiredSlots - 1);
                combinedSlots.add(new TimeSlot(start.getStartTime(), end.getEndTime(), true));
            }
        }
        return new SlotsBean(combinedSlots);
    }

    public void saveBooking(OptionsBean options){
        BookingInterface booking = new ConcreteBooking();
        BookingSession bookingSession = SessionManager.getInstance().getBookingSession();
        Track track = bookingSession.getTrack();
        int bookedSlots = bookingSession.getBookedSlots();
        LocalDate selectedDay = options.getDate();

        ConcreteBooking concreteBooking = (ConcreteBooking) booking;
        concreteBooking.setRental(options.getRental());
        concreteBooking.setPersonal(options.getPersonal());
        concreteBooking.setUser(SessionManager.getInstance().getLoggedUser());
        concreteBooking.setShift(options.getShifts());
        concreteBooking.setTrackName(track.getName());
        concreteBooking.setSelectedDay(selectedDay);

        if(options.isRace()){
            booking = new RaceDecorator(booking, track.getCost(1));
        }

        if(options.isFp()){
            booking = new QualifyingDecorator(booking, track.getCost(2));
        }

        if(options.isQuali()){
            booking = new FreePracticeDecorator(booking, track.getCost(3));
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

        track.addBooking(booking);
        BookingDao bookDao = FactoryDAO.getInstance().createBookingDao();
        bookDao.addBooking(booking);
        System.out.println("Booking " + booking.getId() + " saved");

        bookingSession.setBooking(booking);
        double startTime = options.getStartTime();
        track.setSlotAvailability(selectedDay, bookedSlots, startTime);
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        trackDao.updateTrack(track);
    }
}
