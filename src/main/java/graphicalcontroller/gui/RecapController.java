package graphicalcontroller.gui;

import beans.BookRecapBean;
import controllers.BookManager;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import utils.session.SessionManager;

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
        BookRecapBean recap = bookManager.getBookRecap(); //Recupera dati della prenotazione appena effettuata
        //Mostra i dati della prenotazione
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

    public void proceed() {
        try{
            SessionManager.getInstance().setCurrentSection("Home");
            SceneManager.changeScene("/views/Home.fxml");
        }catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
