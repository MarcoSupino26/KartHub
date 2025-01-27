package models.dao.factory;

import models.booking.BookingDao;
import models.booking.BookingDaoDB;
import models.event.KartEventDaoDB;
import models.event.KartEventDao;

import models.slots.TimeSlotDaoDB;
import models.slots.TimeSlotDao;
import models.track.TrackDaoDB;
import models.track.TrackDao;

import models.user.UserDaoDB;
import models.user.UserDao;

public class DaoDB extends FactoryDAO {
    @Override
    public UserDao createUserDao(){return new UserDaoDB();}

    @Override
    public TrackDao createTrackDao(){return new TrackDaoDB();}

    @Override
    public BookingDao createBookingDao() {return new BookingDaoDB();}

    @Override
    public KartEventDao createKartEventDao(){return new KartEventDaoDB();}

    @Override
    public TimeSlotDao createTimeSlotDao(){return new TimeSlotDaoDB();}
}
