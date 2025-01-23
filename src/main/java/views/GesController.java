package views;

import beans.TrackBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.user.User;
import utils.SessionManager;

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
    private TextField trackName;
    @FXML
    private TextField description;
    @FXML
    private TextField address;



    @FXML
    public void initialize() {
        String style = "-fx-text-fill: white; -fx-background-color: transparent;";
        User logged = SessionManager.getInstance().getLoggedUser();
        trackName.setStyle(style);
        description.setStyle(style);
        address.setStyle(style);
        registration.setVisible(false);
        profile.setText(logged.getUsername() + ",");
        absentTrack.setVisible(true);
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
        new ManageController().createTrack(track);
        SceneManager.changeScene("/slotChoice.fxml");
    }

}
