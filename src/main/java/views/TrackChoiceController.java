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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.util.List;

public class TrackChoiceController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List <DisplayBean> profiles;
        VBox tracksContainer = new VBox(10);
        tracksContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        tracksContainer.setPrefWidth(500);
        tracksContainer.setPrefHeight(100);

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
        trackProfile.setPrefHeight(100);
        trackProfile.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px; -fx-border-color: #c5151d");
        trackProfile.setSpacing(10);
        trackProfile.setAlignment(Pos.CENTER_LEFT);

        ImageView trackPic = new ImageView(display.getImage());
        trackPic.setFitWidth(120);
        trackPic.setFitHeight(100);
        trackPic.setPreserveRatio(true);

        VBox trackDetails = new VBox(10);
        trackDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5; -fx-border-radius: 10;");
        trackDetails.setPrefWidth(500);
        trackDetails.setPrefHeight(100);

        Text name = new Text(display.getName());
        name.setFont(Font.font("Futura-Medium", 22));
        name.setFill(Color.WHITE);
        Text description = new Text(display.getDescription());
        description.setFont(Font.font("Futura-Medium", 16));
        description.setFill(Color.LIGHTGRAY);
        description.setWrappingWidth(350);
        trackDetails.getChildren().addAll(name, description);

        Button selectTrack = new Button("Seleziona");
        selectTrack.setStyle("-fx-background-color: #c5151d; -fx-text-fill: white;");
        selectTrack.setFont(Font.font("Futura-Medium", 18));
        selectTrack.setOnAction(event->onSelectClicked(event, name.getText()));

        trackProfile.getChildren().addAll(trackPic, trackDetails, selectTrack);
        tracksContainer.getChildren().add(trackProfile);
    }

    @FXML
    public void onSelectClicked(ActionEvent event, String name) {
        BookManager bookManager = new BookManager();
        bookManager.setBookingSession(name);
        SceneManager.changeScene("/book.fxml");
    }

}
