package views;

import beans.InfoBean;
import beans.TrackBean;
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
    public void setData(InfoBean track){
        trackName.setText(track.getTrack().getName());
        trackPic.setImage(track.getTrack().getImage());
    }

    @FXML
    public void logout(){
        Session.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void manageTrack(){
        System.out.println("Working on it");
    }
}
