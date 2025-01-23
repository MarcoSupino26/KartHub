package views;

import controllers.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.EventSession;
import utils.SessionManager;

public class PaymentDone {
    @FXML
    private Button events;
    @FXML
    private Button book;

    @FXML
    public void initialize() {
        new EventManager().clearSession();
    }

    @FXML
    public void switchToHome() {
        SceneManager.changeScene("/main.fxml");
    }
}
