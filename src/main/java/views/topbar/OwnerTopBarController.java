package views.topbar;

import controllers.ManageController;
import javafx.fxml.FXML;
import utils.SessionManager;
import views.SceneManager;

public class OwnerTopBarController {

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToManager(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/trackManager.fxml");
        else SceneManager.changeScene("/trackCreation.fxml");
    }

    @FXML
    public void logout(){
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToEventi(){
        if(new ManageController().registeredTrack()) SceneManager.changeScene("/trackevents.fxml");
        else SceneManager.changeScene("/trackCreation.fxml");
    }

}
