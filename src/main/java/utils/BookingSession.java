package utils;

import models.booking.BookingInterface;
import models.track.Track;


public class BookingSession {
    private Track track;
    private int bookedSlots;
    private BookingInterface booking;

    public BookingSession(Track track) {
        this.track = track;
        this.bookedSlots = 0;
    }

    public void setTrack(){this.track = new Track();}

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public void setBookedSlots(int bookedSlots) {this.bookedSlots = bookedSlots;}

    public int getBookedSlots(){return bookedSlots;}

    public void setBooking(BookingInterface booking) {this.booking = booking;}

    public BookingInterface getBooking() {return booking;}
}
