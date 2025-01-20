package utils;

import models.track.Track;

import java.time.LocalDate;

public class BookingSession {
    private Track track;
    private int bookedSlots;

    public BookingSession(Track track) {
        this.track = track;
        this.bookedSlots = 0;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public void setBookedSlots(int bookedSlots) {this.bookedSlots = bookedSlots;}

    public int getBookedSlots(){return bookedSlots;}
}
