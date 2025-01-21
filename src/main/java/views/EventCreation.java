package views;

import beans.EventBeanCreation;
import controllers.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import utils.SessionManager;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventCreation {
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventName;
    @FXML
    private TextField eventType;
    @FXML
    private TextField ticketCost;
    @FXML
    private TextField eventTime;
    @FXML
    private Spinner<Integer> tickets;

    @FXML
    public void initialize(){
        eventDate.setStyle("-fx-font-family: 'Futura-Medium'; -fx-font-size: 18");
        tickets.setStyle("-fx-font-family: 'Futura-Medium'; -fx-font-size: 18");
        tickets.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,0));
    }

    @FXML
    public void createEvent() {
        EventBeanCreation bean = new EventBeanCreation(eventName.getText());
        bean.setDate(eventDate.getValue());
        bean.setPrice(Double.parseDouble(ticketCost.getText()));
        bean.setType(eventType.getText());
        bean.setAvailableTickets((Integer) tickets.getValue());
        bean.setTime(LocalTime.parse(eventTime.getText()));
        bean.setTrack(SessionManager.getInstance().getEventSession().getTrackname());
        new EventManager().saveEvent(bean);
        SessionManager.getInstance().freeEventSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }
}
