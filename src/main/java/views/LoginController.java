package views;

import beans.loginBean;
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
        loginBean logBean = loginBean.getInstance();
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

    @FXML
    public void switchToHome(MouseEvent event){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML public void switchToSign(Event event){
        SceneManager.changeScene("/sign.fxml");
    }
}
