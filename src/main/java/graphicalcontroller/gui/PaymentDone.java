package graphicalcontroller.gui;

import controllers.EventManager;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import utils.session.SessionManager;

public class PaymentDone {

    @FXML
    public void initialize() {
        new EventManager().clearSession();
    }

    @FXML
    public void switchToHome() {
        SessionManager.getInstance().setCurrentSection("Home");
        try {
            SceneManager.changeScene("/views/Home.fxml");
        } catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
