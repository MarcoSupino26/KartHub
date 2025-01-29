package graphicalcontroller.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.session.SessionManager;

public class UserTopBarController {
    @FXML
    private Button log;
    @FXML
    private Button book;
    @FXML
    private Button event;

    private static final String LOGIN = "Login";

    @FXML
    public void initialize(){
        if(SessionManager.getInstance().getLoggedUser()!=null){
            log.setText("Logout");
        }
        updateButtonsStyle();
    }

    private void updateButtonsStyle(){
        String currentSection = SessionManager.getInstance().getCurrentSection();
        setButtonsStyle(book, "Prenota", currentSection);
        setButtonsStyle(event, "Eventi", currentSection);
        setButtonsStyle(log, LOGIN, currentSection);
    }

    private void setButtonsStyle(Button button, String section, String currentSection){
        if(section.equals(currentSection)){
            button.setStyle("-fx-underline: true; -fx-focus-color: transparent; -fx-background-color: transparent;");
        }else{
            button.setStyle("-fx-underline: false; -fx-focus-color: transparent; -fx-background-color: transparent;");
        }
    }

    @FXML
    public void switchToHome(){
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.setCurrentSection("Home");
        if(sessionManager.getLoggedUser()!=null) {
            if (sessionManager.getBookingSession() != null) sessionManager.freeBookingSession();
            if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
        }
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToPrenota(){
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.setCurrentSection("Prenota");
        if(SessionManager.getInstance().getLoggedUser()!=null){
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            SceneManager.changeScene("/views/Book.fxml");
        }
        else switchToLogin();
    }

    @FXML
    public void switchToEventi(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getInstance().getLoggedUser()!=null) {
            sessionManager.setCurrentSection("Eventi");
            SceneManager.changeScene("/views/Events.fxml");;
        }else {
            sessionManager.setCurrentSection(LOGIN);
            SceneManager.changeScene("/views/Login.fxml");
        }
    }

    @FXML
    public void switchToLogin(){
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.setCurrentSection(LOGIN);
        if(sessionManager.getInstance().getLoggedUser() == null){
            SceneManager.changeScene("/views/Login.fxml");
        }else {
            if(sessionManager.getBookingSession()!=null) sessionManager.freeBookingSession();
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            sessionManager.freeSession();
            sessionManager.setCurrentSection("Home");
            SceneManager.changeScene("/views/Home.fxml");
        }
    }
}

