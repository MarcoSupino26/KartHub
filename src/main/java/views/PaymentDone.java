package views;

import controllers.EventManager;
import javafx.fxml.FXML;

public class PaymentDone {

    @FXML
    public void initialize() {
        new EventManager().clearSession();
    }

    @FXML
    public void switchToHome() {
        SceneManager.changeScene("/home.fxml");
    }
}
