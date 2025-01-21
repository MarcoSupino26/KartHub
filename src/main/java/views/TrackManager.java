package views;

import beans.InfoBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.EventSession;
import utils.SessionManager;

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
        SessionManager.getInstance().freeManageSession();
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void manageTrack(){
        System.out.println("Working on it");
    }

    @FXML
    public void seeBookings(){
        SceneManager.changeScene("/trackbookings.fxml");
    }

    @FXML
    public void switchToEventi(){
        EventSession eventSession = new EventSession();
        eventSession.setTrackname(trackName.getText());
        SessionManager.getInstance().createEventSession(eventSession);
        SceneManager.changeScene("/trackevents.fxml");
    }
}
