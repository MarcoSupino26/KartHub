package controllers;
import beans.loginBean;
import models.Dao.FactoryDAO;
import models.Dao.MemoryDao;
import models.Dao.UserDao;
import models.Dao.UserDaoMem;
import models.Session;
import models.User;

public class AuthController {

    public AuthController(){
    }

    public void session_end(){
        Session.getInstance().freeSession();
    }

    public boolean authUser(loginBean logBean){
        UserDaoMem users = UserDaoMem.getInstance();
        String username = logBean.getUsername();
        String passw = logBean.getPassword();
        User usr = users.getUser(username, passw);

        if(usr == null) {
            return false;
        }else{
            Session.getInstance().setLoggedUser(usr);
            return true;
        }
    }

    public void registerUser(loginBean logBean){
        //UserDaoMem users = UserDaoMem.getInstance()
        UserDao users = FactoryDAO.getInstance().createUserDao();
        String username = logBean.getUsername();
        String passw = logBean.getPassword();
        String type = logBean.getType();
        User usr = new User(username, passw, type);
        users.addUser(username, usr);
        Session.getInstance().setLoggedUser(usr);
    }
}
