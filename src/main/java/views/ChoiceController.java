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
import utils.SessionManager;
import javafx.fxml.FXML;

import java.util.List;

public class ChoiceController {

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
        trackProfile.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px;");
        trackProfile.setSpacing(20);
        trackProfile.setAlignment(Pos.CENTER_LEFT);

        ImageView trackPic = new ImageView(display.getImage());
        trackPic.setFitWidth(120);
        trackPic.setFitHeight(100);
        trackPic.setPreserveRatio(true);

        Text name = customize(new Text(display.getName()));
        Text description = customize(new Text(display.getDescription()));

        Button selectTrack = new Button("Select Track");
        selectTrack.setStyle("-fx-background-color: #c5151d; -fx-text-fill: white;");
        selectTrack.setFont(Font.font("Futura-Medium"));
        selectTrack.setOnAction(event->onSelectClicked(event, name.getText()));

        trackProfile.getChildren().addAll(trackPic, description, selectTrack);
        tracksContainer.getChildren().add(trackProfile);
    }

    public Text customize(Text text){
        text.setFont(Font.font("Futura-Medium", 22));
        text.setFill(Color.WHITE);
        return text;
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(){
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void onSelectClicked(ActionEvent event, String name) {
        BookManager bookManager = new BookManager();
        bookManager.setBookingSession(name);
        SceneManager.changeScene("/prenota.fxml");
    }

}
