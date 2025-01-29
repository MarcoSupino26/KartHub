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
    public void accedi() {
        LoginBean logBean = createLoginBean();
        try {
            authenticateUser(logBean);
        } catch (UserNotFoundException e) {
            e.handleException();
        }
    }

    @FXML
    public void switchToSign() {
        try {
            changeScene("/Sign.fxml");
        }catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }

    private LoginBean createLoginBean() {
        LoginBean logBean = new LoginBean();
        logBean.setUsername(usr.getText());
        logBean.setPassword(psw.getText());
        return logBean;
    }

    private void authenticateUser(LoginBean logBean) throws UserNotFoundException {
        if (new AuthController().authUser(logBean)) {
            changeScene("/Home.fxml");
        }
    }

    private void changeScene(String fxmlPath) {
        try {
            SceneManager.changeScene(fxmlPath);
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }
}
