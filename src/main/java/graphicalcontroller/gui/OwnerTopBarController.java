package graphicalcontroller.gui;

import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.session.SessionManager;

public class OwnerTopBarController {
    @FXML
    private Button ges;
    @FXML
    private Button event;

    @FXML
    public void initialize() {
        updateButtonsStyle();
    }

    private void updateButtonsStyle(){
        String currentSection = SessionManager.getInstance().getCurrentSection();
        setButtonsStyle(ges, "Gestisci", currentSection);
        setButtonsStyle(event, "Eventi", currentSection);
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
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        sessionManager.setCurrentSection("Home");
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToManager(){
        SessionManager.getInstance().setCurrentSection("Gestisci");
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/views/trackManager.fxml");
        else SceneManager.changeScene("/views/TrackCreation.fxml");
    }

    @FXML
    public void logout(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SessionManager.getInstance().freeSession();
        sessionManager.setCurrentSection("Home");
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToEventi(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(new ManageController().registeredTrack()) {
            sessionManager.setCurrentSection("Eventi");
            SceneManager.changeScene("/views/TrackEvents.fxml");
        }
        else{
            sessionManager.setCurrentSection("Gestisci");
            SceneManager.changeScene("/views/TrackCreation.fxml");
        }
    }

}
