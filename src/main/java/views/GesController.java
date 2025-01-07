package views;

import beans.TrackBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.user.User;
import utils.Session;

import java.io.File;

public class GesController {
    @FXML
    private Text profile;
    @FXML
    private Group absentTrack;
    @FXML
    private Group registration;
    @FXML
    private ImageView imagePicker;

    @FXML
    public void initialize() {
        User logged = Session.getInstance().getLoggedUser();
        TrackBean track = TrackBean.getInstance();
        track.setOwner(logged);
        ManageController manage = new ManageController();
        if(manage.registeredTrack()){
            absentTrack.setVisible(false);
            registration.setVisible(true);
        }else{
            profile.setText(logged.getUsername() + ",");
            registration.setVisible(false);
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

    @FXML
    public void setRegistration(){
        absentTrack.setVisible(false);
        registration.setVisible(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(registration.getScene().getWindow());
        Image image = new Image(selectedFile.toURI().toString());
        imagePicker.setImage(image);
    }
}
