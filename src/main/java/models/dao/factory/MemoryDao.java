package models.dao.factory;

import models.dao.UserDao;
import models.dao.UserDaoMem;

public class MemoryDao extends FactoryDAO {
    @Override
    public UserDao createUserDao(){
        return UserDaoMem.getInstance();
    }
}
