package views;

import beans.BookingsDisplay;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.ManageSession;
import utils.SessionManager;

import java.util.List;

public class TrackBookings {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        ManageSession mS = SessionManager.getInstance().getManageSession(usr);
        String trackName = mS.getTrackName();

        List<BookingsDisplay> bookings;
        VBox bookingsContainer = new VBox(10);
        bookingsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        ManageController mC = ManageController.getInstance();
        bookings = mC.getBookings(trackName);
        for(BookingsDisplay booking : bookings) {
            displayBookings(booking, bookingsContainer);
        }
        scrollPane.setContent(bookingsContainer);
    }

    public void displayBookings(BookingsDisplay booking, VBox bookingsContainer) {
        HBox bookingDisplayed = new HBox(10);
        bookingDisplayed.setPrefWidth(500);
        bookingDisplayed.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px");
        bookingDisplayed.setSpacing(20);
        bookingDisplayed.setAlignment(Pos.CENTER_LEFT);

        Text user = customize(new Text(booking.getUsr()));
        Text shift = customize(new Text(booking.getShift()));
        Text rental = customize(new Text(booking.getRental()));
        Text personal = customize(new Text(booking.getPersonal()));
        Text description = customize(new Text(booking.getDescription()));
        Text earnings = customize(new Text(booking.getCost()));

        bookingDisplayed.getChildren().addAll(user, shift, rental, personal, description, earnings);

        bookingsContainer.getChildren().add(bookingDisplayed);
    }

    public Text customize(Text text) {
        text.setStyle("-fx-background-color: #000000; -fx-font-size:18px; -fx-fill: white");
        text.setFont(Font.font("Futura-Medium"));
        return text;
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void logout(){
        SessionManager.getInstance().setLoggedUser(null);
        SceneManager.changeScene("/main.fxml");
    }
}
