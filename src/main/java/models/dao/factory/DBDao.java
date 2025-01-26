package models.dao.factory;

import models.booking.BookingDao;
import models.booking.BookingDaoMem;
import models.booking.DBBookingDao;
import models.event.DBKartEventDao;
import models.event.KartEventDao;

import models.event.KartEventDaoMem;
import models.slots.DBTimeSlotDao;
import models.slots.TimeSlotDao;
import models.track.DBTrackDao;
import models.track.TrackDao;

import models.user.DBUserDao;
import models.user.UserDao;

public class DBDao extends FactoryDAO {
    @Override
    public UserDao createUserDao(){return new DBUserDao();}

    @Override
    public TrackDao createTrackDao(){return new DBTrackDao();}

    @Override
    public BookingDao createBookingDao() {return new DBBookingDao();}

    @Override
    public KartEventDao createKartEventDao(){return new DBKartEventDao();}

    @Override
    public TimeSlotDao createTimeSlotDao(){return new DBTimeSlotDao();}
}
