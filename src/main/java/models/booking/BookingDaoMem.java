package models.booking;

import java.util.HashMap;

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
        }else {
            System.out.println("La prenotazione" + id + " è già presente!");
        }
    }
}
