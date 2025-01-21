package views;

import controllers.PaymentManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.w3c.dom.events.Event;
import utils.EventSession;
import utils.SessionManager;

import java.awt.*;

public class PaymentController {
    @FXML
    private Text cost;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField expiryDate;
    @FXML
    private TextField cvv;
    @FXML
    private Button events;
    @FXML
    private Button book;

    @FXML
    public void initialize() {
        EventSession eventSession = SessionManager.getInstance().getEventSession();
        if(eventSession != null) {
            events.setStyle("-fx-underline: true");
            int amount = eventSession.getShoppedTickets();
            amount = amount * eventSession.getShoppedTickets();
            cost.setText(String.valueOf(amount));
        }else {
            book.setStyle("-fx-underline: true");
        }
    }

    @FXML
    public void switchToHome() {
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void makePayment() {
        PaymentManager paymentManager = new PaymentManager();
        String cardName = name.getText();
        String cardSurname = surname.getText();
        String card = cardNumber.getText();
        String expirationDate = expiryDate.getText();
        String securityCode = cvv.getText();
        paymentManager.processPayment(cardName, cardSurname, card, expirationDate, securityCode);
        SceneManager.changeScene("/paymentdone.fxml");
    }
}
