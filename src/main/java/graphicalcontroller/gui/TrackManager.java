package graphicalcontroller.gui;

import beans.TrackProfileBean;
import controllers.ManageController;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
        TrackProfileBean trackProfileBean = new ManageController().getTrackInfo();
        trackName.setText(trackProfileBean.getName());
        trackPic.setImage(trackProfileBean.getImage());
    }

    @FXML
    public void manageTrack(){ //dummy
    }

    @FXML
    public void seeBookings(){
        try{
            SceneManager.changeScene("/views/TrackBookings.fxml");
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
