package views;

import beans.LoginBean;
import controllers.AuthController;
import exceptions.DataLoadException;
import exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usr;
    @FXML
    private TextField psw;

    @FXML
    public void accedi(){
        LoginBean logBean = new LoginBean();
        logBean.setUsername(usr.getText());
        logBean.setPassword(psw.getText());
        try {
            if (new AuthController().authUser(logBean)) {
                try{
                SceneManager.changeScene("/home.fxml");
                } catch (DataLoadException e) {
                    System.out.println(e.getMessage());
                }
            }
        }catch (UserNotFoundException e){
            e.handleException();
        }
    }
    @FXML
    public void switchToSign(){
        try {
            SceneManager.changeScene("/sign.fxml");
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
