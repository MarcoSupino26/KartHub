package views;

import controllers.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.User;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button log;

    @FXML
    private Button book;

    @FXML
    public void initialize(){
        User logged;
        SessionManager session = SessionManager.getInstance();
        logged = session.getLoggedUser();
        if(logged != null) {
            log.setText("Logout");
            if(logged.getType().equals("Owner")){
                book.setText("Gestisci");
            }
        }
    }

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