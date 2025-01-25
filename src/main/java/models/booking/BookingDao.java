package models.booking;

import java.util.List;

public abstract class BookingDao{
   public abstract void addBooking(BookingInterface booking);
   public abstract List<BookingInterface> getBookingsByTrack(String trackName);
}
