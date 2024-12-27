package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EventiController {

    @FXML
    public void switchToHome(MouseEvent event){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToPrenota(ActionEvent event) throws IOException {
        SceneManager.changeScene("/prenota.fxml");
    }

}