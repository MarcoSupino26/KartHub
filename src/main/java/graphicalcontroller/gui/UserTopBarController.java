package graphicalcontroller.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.session.SessionManager;

public class UserTopBarController {
    @FXML
    private Button log;

    @FXML
    public void initialize(){
        if(SessionManager.getInstance().getLoggedUser()!=null){
            log.setText("Logout");
        }
    }

    @FXML
    public void switchToHome(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getLoggedUser()!=null) {
            if (sessionManager.getBookingSession() != null) sessionManager.freeBookingSession();
            if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
        }
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToPrenota(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(SessionManager.getInstance().getLoggedUser()!=null){
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            SceneManager.changeScene("/views/Book.fxml");
        }
        else switchToLogin();
    }

    @FXML
    public void switchToEventi(){
        if(SessionManager.getInstance().getLoggedUser()!=null) SceneManager.changeScene("/views/Events.fxml");
        else SceneManager.changeScene("/views/Login.fxml");
    }

    @FXML
    public void switchToLogin(){
        if(SessionManager.getInstance().getLoggedUser() == null){
            SceneManager.changeScene("/views/Login.fxml");
        }else {
            SessionManager sessionManager = SessionManager.getInstance();
            if(sessionManager.getBookingSession()!=null) sessionManager.freeBookingSession();
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            sessionManager.freeSession();
            SceneManager.changeScene("/views/Home.fxml");
        }
    }
}

