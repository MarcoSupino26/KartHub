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
            String executionMode = Main.getExecutionMode();
            if(executionMode.equals("demo")) {
                instance = new MemoryDao();
            } else if(executionMode.equals("db")) {
                instance = new DaoDB();
            } else if(executionMode.equals("fsys")) {
                instance = new FsysDao();
            }
        }
        return instance;
    }

    public abstract UserDao createUserDao();

    public abstract TrackDao createTrackDao();

    public abstract BookingDao createBookingDao();

    public abstract KartEventDao createKartEventDao();

    public abstract TimeSlotDao createTimeSlotDao();
}
