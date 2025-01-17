package models.dao.factory;

import models.track.TrackDao;
import models.user.UserDao;
import models.booking.BookingDao;

public abstract class FactoryDAO {
    private static FactoryDAO instance;

    public static FactoryDAO getInstance() {
        if (instance == null) {
            instance = new MemoryDao();
        }
        return instance;
    }

    public abstract UserDao createUserDao();

    public abstract TrackDao createTrackDao();

    public abstract BookingDao createBookingDao();
}
