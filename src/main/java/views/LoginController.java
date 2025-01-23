package views;

import beans.LoginBean;
import controllers.AuthController;
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

        AuthController authenticator = new AuthController();
        if(authenticator.authUser(logBean)){
            SceneManager.changeScene("/home.fxml");
        }else {
            SceneManager.changeScene("/login.fxml");
        }
    }
    @FXML
    public void switchToSign(){
        SceneManager.changeScene("/sign.fxml");
    }
}
