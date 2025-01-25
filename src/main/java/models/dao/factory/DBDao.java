package models.dao.factory;

import models.booking.BookingDao;
import models.booking.BookingDaoDB;
import models.booking.BookingDaoMem;
import models.event.KartEventDao;
import models.event.KartEventDaoDB;
import models.event.KartEventDaoMem;
import models.slots.TimeSlotDao;
import models.slots.TimeSlotDaoDB;
import models.track.TrackDao;
import models.track.TrackDaoDB;
import models.track.TrackDaoMem;
import models.user.UserDao;
import models.user.UserDaoDB;
import models.user.UserDaoMem;

public class DBDao extends FactoryDAO {
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
