package models.dao.factory;

import models.booking.BookingDao;
import models.booking.BookingDaoFsys;
import models.event.KartEventDao;
import models.event.KartEventDaoFsys;
import models.slots.TimeSlotDao;
import models.slots.TimeSlotDaoFsys;
import models.track.TrackDao;
import models.track.TrackDaoFsys;
import models.user.UserDao;
import models.user.UserDaoFsys;

public class FsysDao extends FactoryDAO{

    @Override
    public UserDao createUserDao(){return new UserDaoFsys();}

    @Override
    public TrackDao createTrackDao() {return new TrackDaoFsys();}

    @Override
    public BookingDao createBookingDao() {return new BookingDaoFsys();}

    @Override
    public KartEventDao createKartEventDao() {return new KartEventDaoFsys();}

    @Override
    public TimeSlotDao createTimeSlotDao() {return new TimeSlotDaoFsys();}
}
