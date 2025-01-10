package models.dao.factory;

import models.booking.BookingDao;
import models.booking.BookingDaoMem;
import models.track.TrackDao;
import models.track.TrackDaoMem;
import models.user.UserDao;
import models.user.UserDaoMem;

public class MemoryDao extends FactoryDAO {
    @Override
    public UserDao createUserDao(){
        return UserDaoMem.getInstance();
    }

    @Override
    public TrackDao createTrackDao(){return TrackDaoMem.getInstance();}

    @Override
    public BookingDao createBookingDao() {return BookingDaoMem.getInstance();}
}
