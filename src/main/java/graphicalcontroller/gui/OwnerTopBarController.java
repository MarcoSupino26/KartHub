package graphicalcontroller.gui;

import controllers.ManageController;
import javafx.fxml.FXML;
import utils.session.SessionManager;

public class OwnerTopBarController {

    @FXML
    public void switchToHome(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToManager(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/views/trackManager.fxml");
        else SceneManager.changeScene("/views/TrackCreation.fxml");
    }

    @FXML
    public void logout(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/views/Home.fxml");
    }

    @FXML
    public void switchToEventi(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/views/TrackEvents.fxml");
        else SceneManager.changeScene("/views/TrackCreation.fxml");
    }

}
