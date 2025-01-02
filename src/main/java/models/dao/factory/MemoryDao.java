package models.dao.factory;

import models.dao.TimeDao;
import models.dao.TimeDaoMem;
import models.dao.UserDao;
import models.dao.UserDaoMem;

public class MemoryDao extends FactoryDAO {
    @Override
    public UserDao createUserDao(){
        return UserDaoMem.getInstance();
    }

    @Override
    public TimeDao createTimeDao(){return TimeDaoMem.getInstance();}
}
