package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {

    @FXML
    public void switchToPrenota(ActionEvent event) throws IOException {
        SceneManager.changeScene("/prenota.fxml");
    }

    @FXML
    public void switchToEventi(ActionEvent event) throws IOException {
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        SceneManager.changeScene("/login.fxml");
    }
}