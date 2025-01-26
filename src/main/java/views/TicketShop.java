package views;

import beans.EventCreationBean;
import controllers.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class TicketShop {
    @FXML
    private ComboBox<Integer> ticket;
    @FXML
    private Text date;
    @FXML
    private Text hour;
    @FXML
    private Text eventType;
    @FXML
    private Text cost;
    @FXML
    private Text eventName;
    @FXML
    private Text trackName;

    @FXML
    public void initialize() {
        EventManager eventManager = new EventManager();
        EventCreationBean bean = eventManager.getPaymentInfo();
        int avTickets = bean.getAvailableTickets();

        for (int i = 0; i <= avTickets; i++) {
            ticket.getItems().add(i);
        }
        ticket.setValue(0);
        ticket.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                double price = updatePrice(newValue, bean.getPrice());
                cost.setText("€" + String.format("%.2f", price));
            }
        });
        trackName.setText(bean.getTrack());
        eventName.setText(bean.getEventName());
        date.setText(String.valueOf(bean.getDate()));
        hour.setText(String.valueOf(bean.getTime()));
        cost.setText("€0,0");
        eventType.setText(bean.getType());
    }

    @FXML
    public void pay(){
        int selectedTickets = ticket.getValue();
        new EventManager().setSoldTickets(selectedTickets);
        SceneManager.changeScene("/pay.fxml");
    }

    @FXML
    public double updatePrice(int selectedTickets, double price) {
        return price * selectedTickets;
    }
}
