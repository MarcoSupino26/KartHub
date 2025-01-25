package controllers;

import beans.*;
import javafx.scene.image.Image;
import models.booking.*;
import models.dao.factory.FactoryDAO;
import models.slots.TimeSlot;
import models.track.Track;
import models.track.TrackDao;
import utils.session.BookingSession;
import utils.session.SessionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    public boolean isSessionOpen(){
        return SessionManager.getInstance().getBookingSession()!=null;
    }

    public List<DisplayBean> getTracks(){
        List<DisplayBean> disBeans = new ArrayList<>();
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
                SessionManager.getInstance().createBookingSession(bookingSession);
            }
        }
    }

    public void generateSlots2(LocalDate selectedDate){
        BookingSession session = SessionManager.getInstance().getBookingSession();
        Track track = session.getTrack();

        if(!session.getTrack().getTimeSlots(selectedDate).isEmpty()) return;

        List<TimeSlot> timeSlots = getTimeSlotList(track);

        session.getTrack().addTimeSlots(timeSlots, selectedDate);
    }

    private static List<TimeSlot> getTimeSlotList(Track track) {
        double start = track.getOpeningHour();
        double end = track.getClosingHour();
        double duration = track.getShiftDuration();

        int totalMinutes = (int) ((end - start) * 60);
        int numberofSlots = totalMinutes / (int) duration;

        List <TimeSlot> timeSlots = new ArrayList<>();

        for(int i = 0; i < numberofSlots; i++){
            double startSlot = start + ((duration * i) % 60)/100.0 + (int) (i / (60/duration));
            double endSlot = start + ((duration * (i+1)) % 60)/100.0 + (int) ((i+1) / (60/duration));
            TimeSlot slot = new TimeSlot(startSlot, endSlot, true);
            timeSlots.add(slot);
        }
        return timeSlots;
    }

    public List<SlotBean> getSlots2(LocalDate day){
        BookingSession session = SessionManager.getInstance().getBookingSession();
        List<TimeSlot> timeSlots = session.getTrack().getTimeSlots(day);
        List<SlotBean> daySlots = new ArrayList<>();

        for(TimeSlot timeSlot : timeSlots){
            double start = timeSlot.getStartTime();
            double end = timeSlot.getEndTime();
            boolean available = timeSlot.isAvailable();
            SlotBean slotBean = new SlotBean(start, end, available);
            daySlots.add(slotBean);
        }

        return daySlots;
    }

    public List<SlotBean> getCombinedSlots2(CombinedSlotsBean bean){
        int requiredSlots = 0;
        List<SlotBean> generatedSlots = bean.getGeneratedSlots();
        List<SlotBean> combinedSlots = new ArrayList<>();

        if(bean.isRaceChecked()) requiredSlots += 2;
        if(bean.isQualiChecked()) requiredSlots += 1;
        if(bean.isFpChecked()) requiredSlots += 1;

        SessionManager.getInstance().getBookingSession().setBookedSlots(requiredSlots);

        for (int i = 0; i <= generatedSlots.size() - requiredSlots; i++) {
            boolean allAvailable = true;
            for(int j = 0; j < requiredSlots; j++){
                if(!generatedSlots.get(i+j).isFree()){
                    allAvailable = false;
                    break;
                }
            }
            if (allAvailable){
                SlotBean start = generatedSlots.get(i);
                SlotBean end = generatedSlots.get(i + requiredSlots - 1);

                combinedSlots.add(new SlotBean(start.getSlotStart(), end.getSlotEnd(), true));
            }
        }
        return combinedSlots;
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

        bookingSession.setBooking(booking);
        double startTime = options.getStartTime();
        track.setSlotAvailability(selectedDay, bookedSlots, startTime);
        TrackDao trackDao = FactoryDAO.getInstance().createTrackDao();
        trackDao.insertTrack(track);
    }

    public BookRecapBean getBookRecap(){
        BookingInterface booking = SessionManager.getInstance().getBookingSession().getBooking();
        BookRecapBean bean = new BookRecapBean();
        bean.setRentalKarts(booking.getRental());
        bean.setPersonalKarts(booking.getPersonal());
        bean.setBookingCost(booking.getCost());
        bean.setBookingID(booking.getId());
        bean.setBookingDesc(booking.getDescription());
        bean.setBookedTrack(booking.getTrackName());
        bean.setShift(booking.getShift());
        return bean;
    }

    public void clearSession(){
        SessionManager.getInstance().freeBookingSession();
    }
}
