package controllers;
import beans.LoginBean;
import models.dao.factory.FactoryDAO;
import models.user.UserDao;
import utils.Session;
import models.user.User;

public class AuthController {

    public AuthController(){
        //il construttore non ha bisogno di inizializzare nulla
    }

    public void sessionEnd(){
        Session.getInstance().freeSession();
    }

    public boolean authUser(LoginBean logBean){

        /*UserDaoMem users = UserDaoMem.getInstance();
        String username = logBean.getUsername();
        String passw = logBean.getPassword();
        User usr = users.getUser(username, passw);*/
        UserDao userDAO = FactoryDAO.getInstance().createUserDao();
        String username = logBean.getUsername();
        String password = logBean.getPassword();
        User usr = userDAO.getUser(username, password);
        if(usr == null) {
            return false;
        }else{
            Session.getInstance().setLoggedUser(usr);
            return true;
        }
    }

    public void registerUser(LoginBean logBean){
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
