package models.booking;

import models.dao.factory.FactoryDAO;

public abstract class BookingDao{
   public abstract void addBooking(Booking booking);
}
