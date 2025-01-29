package views.topbar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.session.SessionManager;
import views.SceneManager;

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
        SceneManager.changeScene("/Home.fxml");
    }

    @FXML
    public void switchToPrenota(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(SessionManager.getInstance().getLoggedUser()!=null){
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            SceneManager.changeScene("/Book.fxml");
        }
        else switchToLogin();
    }

    @FXML
    public void switchToEventi(){
        if(SessionManager.getInstance().getLoggedUser()!=null) SceneManager.changeScene("/Events.fxml");
        else SceneManager.changeScene("/Login.fxml");
    }

    @FXML
    public void switchToLogin(){
        if(SessionManager.getInstance().getLoggedUser() == null){
            SceneManager.changeScene("/Login.fxml");
        }else {
            SessionManager sessionManager = SessionManager.getInstance();
            if(sessionManager.getBookingSession()!=null) sessionManager.freeBookingSession();
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            sessionManager.freeSession();
            SceneManager.changeScene("/Home.fxml");
        }
    }
}

