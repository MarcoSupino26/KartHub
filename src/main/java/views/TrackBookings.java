package views;

import beans.BookingsDisplayBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        List<BookingsDisplayBean> bookings;
        VBox bookingsContainer = new VBox(10);
        bookingsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");

        bookings = new ManageController().getBookings();
        for(BookingsDisplayBean booking : bookings) {
            displayBookings(booking, bookingsContainer);
        }
        scrollPane.setContent(bookingsContainer);
    }

    public void displayBookings(BookingsDisplayBean booking, VBox bookingsContainer) {
        HBox bookingDisplayed = new HBox(10);
        bookingDisplayed.setPrefWidth(500);
        bookingDisplayed.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px");
        bookingDisplayed.setSpacing(20);
        bookingDisplayed.setAlignment(Pos.CENTER_LEFT);

        VBox bookingDetails = new VBox(10);
        bookingDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5px; -fx-border-radius: 10px");

        Text user = customize(new Text(booking.getUsr()));
        Text shift = customize(new Text(booking.getShift()));
        Text rental = customize(new Text(booking.getRental()));
        Text personal = customize(new Text(booking.getPersonal()));
        Text description = customize(new Text(booking.getDescription()));
        Text earnings = customize(new Text(booking.getCost()));

        bookingDetails.getChildren().addAll(user, shift, rental, personal, description, earnings);

        bookingDisplayed.getChildren().add(bookingDetails);

        bookingsContainer.getChildren().add(bookingDisplayed);

        bookingsContainer.requestLayout();
    }

    public Text customize(Text text) {
        text.setFont(Font.font("Futura-Medium", 22));
        text.setFill(Color.WHITE);
        return text;
    }
}
