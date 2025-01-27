package controllers;
import beans.LoginBean;
import exceptions.DataLoadException;
import exceptions.UserNotFoundException;
import models.dao.factory.FactoryDAO;
import models.user.Customer;
import models.user.Owner;
import models.user.UserDao;
import utils.session.SessionManager;
import models.user.User;

public class AuthController {

    public AuthController(){//la new non richiede alcun parametro
    }

    public boolean authUser(LoginBean logBean){//Login
        UserDao userDAO = FactoryDAO.getInstance().createUserDao();
        String username = logBean.getUsername();
        String password = logBean.getPassword();
        User usr = null;
        try {
            usr = userDAO.getUser(username, password);
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
        if(usr == null) {
            throw new UserNotFoundException();
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
        if(type.equals("Proprietario")) {
            Owner usr = new Owner(username, passw, type);
            try {
                users.addUser(username, usr);
            }catch (DataLoadException e){
                System.out.println(e.getMessage());
            }
            SessionManager.getInstance().setLoggedUser(usr);
        }else {
            Customer usr = new Customer(username, passw, type);
            try {
                users.addUser(username, usr);
            }catch (DataLoadException e){
                System.out.println(e.getMessage());
            }
            SessionManager.getInstance().setLoggedUser(usr);
        }
    }
}
