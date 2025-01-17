package views;

import controllers.AuthController;
import controllers.ManageController;
import utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.user.User;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button log;

    @FXML
    private Button book;

    @FXML
    public void initialize(){
        User logged;
        SessionManager sessionManager = SessionManager.getInstance();
        logged = sessionManager.getLoggedUser();
        if(logged != null) {
            log.setText("Logout");
            if(logged.getType().equals("Proprietario")){
                book.setText("Gestisci");
            }
        }
    }

    @FXML
    public void switchToPrenota(ActionEvent event){
        User logged = SessionManager.getInstance().getLoggedUser();
        if(logged == null) {
            SceneManager.changeScene("/login.fxml");
        }else if (logged.getType().equals("Proprietario")){
            if(ManageController.getInstance().registeredTrack()){
                SceneManager.changeScene("/trackmanager.fxml");
            }else{
                SceneManager.changeScene("/ges.fxml");
            }
        }
        else {
            SceneManager.changeScene("/prenota.fxml");
        }
    }

    @FXML
    public void switchToEventi(ActionEvent event){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        if(SessionManager.getInstance().getLoggedUser() == null){
            SceneManager.changeScene("/login.fxml");
        }else {
            AuthController auth = new AuthController();
            auth.sessionEnd();
            SceneManager.changeScene("/main.fxml");
        }
    }
}