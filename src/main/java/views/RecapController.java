package views;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
        BookingSession bS = SessionManager.getInstance().getBookingSession(usr);
        BookingInterface booking = bS.getBooking();
        usrname.setText(usr + ", ecco la tua prenotazione");
        rental.setText(String.valueOf(booking.getRental()));
        personal.setText(String.valueOf(booking.getPersonal()));
        cost.setText(String.valueOf(booking.getCost()));
        id.setText(String.valueOf(booking.getId()));
        description.setText(booking.getDescription());
        trackName.setText(booking.getTrackName());
        shift.setText(booking.getShift());
        SessionManager.getInstance().freeBookingSession();
    }
    @FXML
    public void switchToEventi(){
        SessionManager.getInstance().freeBookingSession();
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToHome(){
        SessionManager.getInstance().freeBookingSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(Event event){
        SessionManager.getInstance().freeBookingSession();
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void proceed(){
        SessionManager.getInstance().freeBookingSession();
        switchToHome();
    }
}
