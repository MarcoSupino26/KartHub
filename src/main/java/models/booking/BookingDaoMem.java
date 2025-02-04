package models.booking;

import java.util.HashMap;
import java.util.List;

public class BookingDaoMem extends BookingDao {
    private final HashMap<String, BookingInterface> bookings;
    private static BookingDaoMem instance;

    protected BookingDaoMem() {
        bookings = new HashMap<>();
    }
    public static BookingDaoMem getInstance() {
        if (instance == null) {
            instance = new BookingDaoMem();
        }
        return instance;
    }

    @Override
    public void addBooking(BookingInterface booking) {
        String id = booking.getId();
        if(!bookings.containsKey(id)){
            bookings.put(id, booking);
        }
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
