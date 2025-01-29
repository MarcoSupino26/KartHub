package views.topbar;

import controllers.ManageController;
import javafx.fxml.FXML;
import utils.session.SessionManager;
import views.SceneManager;

public class OwnerTopBarController {

    @FXML
    public void switchToHome(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SceneManager.changeScene("/Home.fxml");
    }

    @FXML
    public void switchToManager(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/trackManager.fxml");
        else SceneManager.changeScene("/TrackCreation.fxml");
    }

    @FXML
    public void logout(){
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/Home.fxml");
    }

    @FXML
    public void switchToEventi(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/TrackEvents.fxml");
        else SceneManager.changeScene("/TrackCreation.fxml");
    }

}
