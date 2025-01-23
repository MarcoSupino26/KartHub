package views;

import beans.PaymentBean;
import controllers.EventManager;
import controllers.PaymentManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    public void initialize() {
        EventManager eventManager = new EventManager();
        double amount = eventManager.getTicketCost();
        amount = amount * eventManager.getSoldTickets();
        cost.setText(String.format("€%.2f",amount));
    }

    @FXML
    public void makePayment() {
        PaymentManager paymentManager = new PaymentManager();
        PaymentBean paymentBean = new PaymentBean();
        paymentBean.setCardName(name.getText());
        paymentBean.setCardSurname(surname.getText());
        paymentBean.setCardNumber(cardNumber.getText());
        paymentBean.setExpiryMonth(expiryDate.getText());
        paymentBean.setSecurityCode(cvv.getText());
        if(paymentManager.processPayment(paymentBean)) SceneManager.changeScene("/paymentdone.fxml");
        else SceneManager.changeScene("/home.fxml");
    }
}
