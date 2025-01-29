package graphicalcontroller.gui;

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

import java.util.List;

public class TrackBookings {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List<BookingsDisplayBean> bookings;
        VBox bookingsContainer = new VBox(10);
        bookingsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        bookingsContainer.setPrefWidth(500);
        bookingsContainer.setPrefHeight(100);

        bookings = new ManageController().getBookings(); //Bean per le info delle prenotazioni
        //Vengono generati dinamicamente oggetti HBox per mostrare le prenotazioni di un tracciato
        for(BookingsDisplayBean booking : bookings) {
            displayBookings(booking, bookingsContainer);
        }
        scrollPane.setContent(bookingsContainer);
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

        Text user = new Text(booking.getUsr());
        user.setFont(Font.font("Futura-Medium", 20));
        user.setFill(Color.WHITE);

        Text date = customize(new Text(String.valueOf(booking.getDate())));
        Text shift = customize(new Text(booking.getShift()));
        Text rental = customize(new Text("Kart noleggiati: " + booking.getRental()));
        Text personal = customize(new Text("Personali: " + booking.getPersonal()));
        Text description = customize(new Text(booking.getDescription()));
        double cost = Double.parseDouble(booking.getCost());
        Text earnings = customize(new Text(String.format("€%.2f", cost)));

        bookingDetails.getChildren().addAll(user, date ,shift, rental, personal, description, earnings);

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
