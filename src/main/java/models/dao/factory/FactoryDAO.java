package models.dao.factory;

import models.event.KartEventDao;
import models.slots.TimeSlotDao;
import models.track.TrackDao;
import models.user.UserDao;
import models.booking.BookingDao;
import start.Main;

public abstract class FactoryDAO {
    private static FactoryDAO instance;

    protected FactoryDAO() {//La new non ha bisogno di un parametro
    }

    public static FactoryDAO getInstance() {
        if (instance == null) {
            if(Main.isDemoMode()) {
                instance = new MemoryDao();
            } else instance = new DBDao();
        }
        return instance;
    }

    public abstract UserDao createUserDao();

    public abstract TrackDao createTrackDao();

    public abstract BookingDao createBookingDao();

    public abstract KartEventDao createKartEventDao();

    public abstract TimeSlotDao createTimeSlotDao();
}
