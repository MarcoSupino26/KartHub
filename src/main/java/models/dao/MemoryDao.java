package models.dao;

public class MemoryDao extends FactoryDAO{
    @Override
    public UserDao createUserDao(){
        return UserDaoMem.getInstance();
    }
}
