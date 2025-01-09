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
    private TextField trackName;
    @FXML
    private TextField description;
    @FXML
    private TextField address;
    @FXML
    private Group shifts;
    @FXML
    private TextField karts;
    @FXML
    private TextField opening;
    @FXML
    private TextField closing;
    @FXML
    private Text ownerName;


    @FXML
    public void initialize() {
        trackName.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        description.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        address.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        shifts.setVisible(false);
        absentTrack.setVisible(false);
        registration.setVisible(false);
        User logged = Session.getInstance().getLoggedUser();
        TrackBean track = TrackBean.getInstance();
        track.setOwner(logged);
        ManageController manage = new ManageController();
        if(manage.registeredTrack()){
            System.out.println("Still da fare");
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
        TrackBean track = TrackBean.getInstance();
        track.setImage(image);
        track.setOwner(Session.getInstance().getLoggedUser());
        track.setName(trackName.getText());
        track.setDescription(description.getText());
        track.setAddress(address.getText());
    }

    @FXML
    public void switchToShifts(){
        registration.setVisible(false);
        ownerName.setText(Session.getInstance().getLoggedUser().getUsername() + ", ci siamo quasi");
        shifts.setVisible(true);
        TrackBean track = TrackBean.getInstance();
        track.setOpeningHour(opening.getText());
        track.setClosingHour(closing.getText());
        track.setAvailableKarts(Integer.parseInt(karts.getText()));
    }

    @FXML
    public void saveTrack(){
        System.out.println("In lavorazione...");
    }
}
