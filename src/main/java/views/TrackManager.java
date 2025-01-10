package views;

import beans.InfoBean;
import beans.TrackBean;
import controllers.ManageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.Session;

public class TrackManager {

    @FXML
    private Text trackName;
    @FXML
    private ImageView trackPic;

    @FXML
    public void initialize() {
        getInfo();
    }

    public void getInfo(){
        InfoBean infoBean = ManageController.getInstance().getTrackInfo();
        trackName.setText(infoBean.getName());
        trackPic.setImage(infoBean.getImage());
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

    @FXML
    public void manageTrack(){
        System.out.println("Working on it");
    }
}
