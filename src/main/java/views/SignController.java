package views;

import beans.LoginBean;
import controllers.AuthController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignController {

    @FXML
    private RadioButton opt1;
    @FXML
    private RadioButton opt2;
    @FXML
    private TextField usr;
    @FXML
    private PasswordField psw;

    @FXML
    public void switchToHome(Event event){
        SceneManager.changeScene("/main.fxml");
    }

    public void initialize(){
        ToggleGroup toggle = new ToggleGroup();
        opt1.setToggleGroup(toggle);
        opt2.setToggleGroup(toggle);
    }

    @FXML
    public void registrazione(ActionEvent event){
        RadioButton selected = (RadioButton) opt1.getToggleGroup().getSelectedToggle();
        LoginBean signBean = LoginBean.getInstance();
        signBean.setUsername(usr.getText());
        signBean.setPassword(psw.getText());
        signBean.setType(selected.getText());

        AuthController auth = new AuthController();
        auth.registerUser(signBean);
        SceneManager.changeScene("/main.fxml");
    }
}
