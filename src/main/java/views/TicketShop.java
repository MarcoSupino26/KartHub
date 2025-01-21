package views;

import controllers.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import utils.EventSession;
import utils.SessionManager;

public class TicketShop {
    @FXML
    private ComboBox ticket;

    @FXML
    public void initialize() {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        int avTickets = eventSession.getCurrentKartEvent().getTickets();

        for (int i = 0; i < avTickets; i++) {
            ticket.getItems().add(i);
        }
        ticket.setValue(0);
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(){
        SessionManager.getInstance().freeEventSession();
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void pay(){
        int selectedTickets = (int) ticket.getValue();
        SessionManager.getInstance().getEventSession().setShoppedTickets(selectedTickets);
        SceneManager.changeScene("/pay.fxml");
    }
}
