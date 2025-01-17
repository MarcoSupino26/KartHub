package controllers;
import beans.LoginBean;
import models.dao.factory.FactoryDAO;
import models.user.UserDao;
import utils.SessionManager;
import models.user.User;

public class AuthController {

    public AuthController(){
        //il construttore non ha bisogno di inizializzare nulla
    }

    public void sessionEnd(){
        SessionManager.getInstance().freeSession();
    }

    public boolean authUser(LoginBean logBean){
        UserDao userDAO = FactoryDAO.getInstance().createUserDao();
        String username = logBean.getUsername();
        String password = logBean.getPassword();
        User usr = userDAO.getUser(username, password);
        if(usr == null) {
            return false;
        }else{
            SessionManager.getInstance().setLoggedUser(usr);
            return true;
        }
    }

    public void registerUser(LoginBean logBean){
        UserDao users = FactoryDAO.getInstance().createUserDao();
        String username = logBean.getUsername();
        String passw = logBean.getPassword();
        String type = logBean.getType();
        User usr = new User(username, passw, type);
        users.addUser(username, usr);
        SessionManager.getInstance().setLoggedUser(usr);
    }
}
