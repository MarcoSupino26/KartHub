package views;

import beans.BookRecapBean;
import controllers.BookManager;
import javafx.fxml.FXML;
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
        BookManager bookManager = new BookManager();
        BookRecapBean recap = bookManager.getBookRecap();
        usrname.setText(usr + ", ecco la tua prenotazione");
        rental.setText(String.valueOf(recap.getRentalKarts()));
        personal.setText(String.valueOf(recap.getPersonalKarts()));
        cost.setText(String.format("€%.2f", recap.getBookingCost()));
        id.setText(String.valueOf(recap.getBookingID()));
        description.setText(recap.getBookingDesc());
        trackName.setText(recap.getBookedTrack());
        shift.setText(recap.getShift());
        bookManager.clearSession();
    }

    public void proceed(){
        SceneManager.changeScene("/home.fxml");
    }
}
