package controllers;
import beans.loginBean;
import models.Dao.UserDAO;
import models.User;

public class AuthController {

    public AuthController(){
    }

    public boolean authUser(loginBean logBean){
        UserDAO users = UserDAO.getInstance();
        String username = logBean.getUsername();
        String passw = logBean.getPassword();
        User usr = users.getUser(username, passw);
        if(usr == null) {
            return false;
        }else{
            return true;
        }
    }
}
