package models.booking;

import java.util.List;

public class BookingDaoFsys extends BookingDao{
    //Non implementato
    @Override
    public void addBooking(BookingInterface booking) {
        return;
    }

    @Override
    public List<BookingInterface> getBookingsByTrack(String trackName) {
        return List.of();
    }

    @Override
    public List<BookingInterface> getBookingsByUser(String usrname) {
        return List.of();
    }
}
