package views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import models.booking.BookingInterface;
import utils.BookingSession;
import utils.SessionManager;

public class RecapController {
    @FXML
    private Text usrname;
    @FXML
    private Text rental;
    @FXML
    private Text personal;
    @FXML
    private Text cost;
    @FXML
    private Text id;
    @FXML
    private Text description;
    @FXML
    private Text trackName;
    @FXML
    private Text shift;

    public void initialize() {
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        BookingSession bS = SessionManager.getInstance().getBookingSession();
        BookingInterface booking = bS.getBooking();
        usrname.setText(usr + ", ecco la tua prenotazione");
        rental.setText(String.valueOf(booking.getRental()));
        personal.setText(String.valueOf(booking.getPersonal()));
        cost.setText(String.format("%.2f", booking.getCost()));
        id.setText(String.valueOf(booking.getId()));
        description.setText(booking.getDescription());
        trackName.setText(booking.getTrackName());
        shift.setText(booking.getShift());
        SessionManager.getInstance().freeBookingSession();
    }

    public void proceed(){
        SceneManager.changeScene("/main.fxml");
    }
}
