package views;

import beans.TrackBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import models.user.User;
import utils.Session;

public class GesController {
    @FXML
    private Text profile;
    @FXML
    private Group absentTrack;

    @FXML
    public void initialize() {
        User logged = Session.getInstance().getLoggedUser();
        TrackBean track = TrackBean.getInstance();
        track.setOwner(logged);
        ManageController manage = new ManageController();
        if(manage.registeredTrack()){
            absentTrack.setVisible(false);
        }else{
            profile.setText(logged.getUsername() + ",");
            absentTrack.setVisible(true);
        }
    }

    @FXML
    public void logout(){
        Session.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }
}
