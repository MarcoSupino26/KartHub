package views;

import beans.LoginBean;
import controllers.AuthController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private TextField usr;
    @FXML
    private TextField psw;

    @FXML
    public void accedi(ActionEvent event){
        LoginBean logBean = LoginBean.getInstance();
        logBean.setUsername(usr.getText());
        logBean.setPassword(psw.getText());

        AuthController authenticator = new AuthController();
        if(authenticator.authUser(logBean)){
            System.out.println("Benvenuto " + logBean.getUsername());
            SceneManager.changeScene("/main.fxml");
        }else {
            System.out.println("Login fallito");
            SceneManager.changeScene("/login.fxml");
        }
    }
    @FXML public void switchToSign(Event event){
        SceneManager.changeScene("/sign.fxml");
    }
}
