package views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrenotaController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToEventi(ActionEvent event) throws IOException {
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToHome(Event event) throws IOException {
        SceneManager.changeScene("/main.fxml");
    }
}
