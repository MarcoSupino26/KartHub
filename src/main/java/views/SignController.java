package views;

import beans.LoginBean;
import controllers.AuthController;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private DatePicker birthDate;

    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        opt1.setToggleGroup(group);
        opt2.setToggleGroup(group);
        //Data di nascita fittizia, solo per fattori estetici
        birthDate.setStyle("-fx-font-family: 'Futura-Medium'; -fx-font-size: 14");
    }

    @FXML
    public void registrazione(){
        RadioButton selected = (RadioButton) opt1.getToggleGroup().getSelectedToggle();
        LoginBean signBean = new LoginBean();
        signBean.setUsername(usr.getText());
        signBean.setPassword(psw.getText());
        signBean.setType(selected.getText());

        AuthController auth = new AuthController();
        auth.registerUser(signBean);
        try {
            SceneManager.changeScene("/Home.fxml");
        } catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
