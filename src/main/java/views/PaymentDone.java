package views;

import controllers.EventManager;
import exceptions.DataLoadException;
import javafx.fxml.FXML;

public class PaymentDone {

    @FXML
    public void initialize() {
        new EventManager().clearSession();
    }

    @FXML
    public void switchToHome() {
        try {
            SceneManager.changeScene("/home.fxml");
        } catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
