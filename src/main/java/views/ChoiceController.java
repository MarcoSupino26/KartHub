package views;
import beans.DisplayBean;
import controllers.BookManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.track.Track;
import utils.Session;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.util.List;

public class ChoiceController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List <DisplayBean> profiles;
        VBox tracksContainer = new VBox(10);
        tracksContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        BookManager bookManager = new BookManager();
        profiles = bookManager.getTracks();
        for (DisplayBean displayBean : profiles) {
            displayTrackProfile(displayBean, tracksContainer);
        }
        scrollPane.setContent(tracksContainer);
    }

    public void displayTrackProfile(DisplayBean display, VBox tracksContainer) {
        HBox trackProfile = new HBox();
        trackProfile.setPrefWidth(500);
        trackProfile.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px;");
        trackProfile.setSpacing(20);
        trackProfile.setAlignment(Pos.CENTER_LEFT);
        ImageView trackPic = new ImageView(display.getImage());
        trackPic.setFitWidth(120);
        trackPic.setFitHeight(100);
        trackPic.setPreserveRatio(true);

        Text description = new Text(display.getDescription());
        description.setStyle("-fx-background-color: #000000; -fx-font-size: 18px; -fx-fill: white;");
        description.setFont(Font.font("Futura Medium"));
        Button selectTrack = new Button("Select Track");
        selectTrack.setStyle("-fx-background-color: #c5151d; -fx-text-fill: white;");
        selectTrack.setFont(Font.font("Futura Medium"));
        selectTrack.setOnAction(this::onSelectClicked);

        trackProfile.getChildren().addAll(trackPic, description, selectTrack);

        tracksContainer.getChildren().add(trackProfile);
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
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
    public void onSelectClicked(ActionEvent event) {
        SceneManager.changeScene("/prenota.fxml");
    }

}