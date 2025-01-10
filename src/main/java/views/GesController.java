package views;

import beans.TrackBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.track.Track;
import models.user.User;
import utils.Session;

import java.io.File;

import static java.lang.Integer.parseInt;

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
    private TextField trackName;
    @FXML
    private TextField description;
    @FXML
    private TextField address;



    @FXML
    public void initialize() {
        User logged = Session.getInstance().getLoggedUser();
        ManageController.getInstance().registeredTrack(); //utilizzare le eccezioni per gestire questo
        trackName.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        description.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        address.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        absentTrack.setVisible(false);
        registration.setVisible(false);
        profile.setText(logged.getUsername() + ",");
        absentTrack.setVisible(true);
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

    @FXML
    public void switchToShifts(){
        TrackBean track = new TrackBean();
        track.setImage(imagePicker.getImage());
        track.setName(trackName.getText());
        track.setDescription(description.getText());
        track.setAddress(address.getText());
        ManageController.getInstance().saveTrack(track);
        SceneManager.changeScene("/slotchoice.fxml");
        SceneManager.showScene();
    }
}
