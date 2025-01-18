package models.booking;

import java.util.HashMap;

public class BookingDaoMem extends BookingDao {
    private final HashMap<Integer, ConcreteBooking> bookings;
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
    public void addBooking(ConcreteBooking concreteBooking) {
        int id = concreteBooking.getId();
        if(!bookings.containsKey(id)){
            bookings.put(id, concreteBooking);
        }else {
            System.out.println("La prenotazione" + id + " è già presente!");
        }
    }
}
