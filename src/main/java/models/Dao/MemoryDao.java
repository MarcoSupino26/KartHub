package models.Dao;

public class MemoryDao extends FactoryDAO{
    @Override
    public UserDao createUserDao(){
        return UserDaoMem.getInstance();
    }
}
