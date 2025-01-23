package models.dao.factory;

import models.event.KartEventDao;
import models.track.TrackDao;
import models.user.UserDao;
import models.booking.BookingDao;

public abstract class FactoryDAO {
    private static FactoryDAO instance;

    protected FactoryDAO() {//La new non ha bisogno di un parametro
    }

    public static FactoryDAO getInstance() {
        if (instance == null) {
            instance = new MemoryDao();
        }
        return instance;
    }

    public abstract UserDao createUserDao();

    public abstract TrackDao createTrackDao();

    public abstract BookingDao createBookingDao();

    public abstract KartEventDao createKartEventDao();
}
