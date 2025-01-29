package views;

import beans.BookingsDisplayBean;
import controllers.BookManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class UserBookings {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List<BookingsDisplayBean> bookings;
        VBox userBookingsContainer = new VBox(10);
        userBookingsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        userBookingsContainer.setPrefWidth(500);
        userBookingsContainer.setPrefHeight(100);

        bookings = new BookManager().getUserBookings();

        for(BookingsDisplayBean booking : bookings) {
            displayBookings(booking, userBookingsContainer);
        }
        scrollPane.setContent(userBookingsContainer);
    }

    public void displayBookings(BookingsDisplayBean booking, VBox bookingsContainer) {//Personalizzazione delle HBox
        HBox bookingDisplayed = new HBox();
        bookingDisplayed.setPrefWidth(500);
        bookingDisplayed.setPrefHeight(100);
        bookingDisplayed.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-color: #c5151d; -fx-border-radius: 10px");
        bookingDisplayed.setSpacing(10);
        bookingDisplayed.setAlignment(Pos.CENTER_LEFT);

        VBox bookingDetails = new VBox(10);
        bookingDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5px; -fx-border-radius: 10px");
        bookingDetails.setPrefWidth(500);
        bookingDetails.setPrefHeight(100);

        Text track = new Text(booking.getTrackName());
        track.setFont(Font.font("Futura-Medium", 20));
        track.setFill(Color.WHITE);

        Text day = customize(new Text(String.valueOf(booking.getSelectedDay())));
        Text slot = customize(new Text(booking.getShift()));
        Text karts = customize(new Text("Kart noleggiati: " + booking.getRental()));
        Text personal = customize(new Text("Personali: " + booking.getPersonal()));
        Text desc = customize(new Text(booking.getDescription()));
        double cost = Double.parseDouble(booking.getCost());
        Text payment = customize(new Text(String.format("€%.2f", cost)));

        bookingDetails.getChildren().addAll(track, day, slot, karts, personal, desc, payment);

        bookingDisplayed.getChildren().add(bookingDetails);

        bookingsContainer.getChildren().add(bookingDisplayed);

        bookingsContainer.requestLayout();
    }

    public Text customize(Text text) {
        text.setFont(Font.font("Futura-Medium", 16));
        text.setFill(Color.LIGHTGRAY);
        return text;
    }
}
