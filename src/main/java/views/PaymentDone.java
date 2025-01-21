package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.EventSession;
import utils.SessionManager;

public class PaymentDone {
    @FXML
    private Button events;
    @FXML
    private Button book;

    @FXML
    public void initialize(){
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        if(eventSession != null){
            events.setStyle("-fx-underline: true");
        }else {
            book.setStyle("-fx-underline: false");
        }
    }

    @FXML
    public void switchToHome() {
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(){
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        if (eventSession != null) {
            SessionManager.getInstance().freeEventSession();
        }else {
            SessionManager.getInstance().freeBookingSession();
        }
        SessionManager.getInstance().freeEventSession();
    }
}
