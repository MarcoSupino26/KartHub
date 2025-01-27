package models.user;

import models.booking.BookingInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer extends User {
    private HashMap<String, BookingInterface> bookings;

    public Customer(String name, String username, String password) {
        super(name, username, password);
        bookings = new HashMap<>();
    }

    public void addBooking(BookingInterface booking) {
        bookings.put(booking.getId(), booking);
    }

    public List<BookingInterface> getBookings() {
        return new ArrayList<>(bookings.values());
    }

    public void setBookings(List<BookingInterface> bookings) {
        for (BookingInterface booking : bookings) {
            addBooking(booking);
        }
    }
}
