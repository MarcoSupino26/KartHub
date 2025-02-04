package graphicalcontroller.gui;

import beans.EventCreationBean;
import controllers.EventManager;
import exceptions.DataLoadException;
import exceptions.InvalidDateException;
import exceptions.InvalidDateFormatException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import utils.session.SessionManager;

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
        tickets.setStyle("-fx-font-family: 'Futura-Medium'; -fx-font-size: 18; -fx-border-radius: 10; -fx-background-color: #000000");
        tickets.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,0));
    }

    @FXML
    public void createEvent() {
        EventCreationBean bean = new EventCreationBean(eventName.getText());

        try {
            if(eventDate.getValue().isBefore(LocalDate.now())) {
                throw new InvalidDateException();
            }
        } catch (InvalidDateException e) {
            e.handleException();
        } catch (InvalidDateFormatException e){
            e.handleException();
        }
        bean.setDate(eventDate.getValue());

        bean.setPrice(Double.parseDouble(ticketCost.getText()));
        bean.setType(eventType.getText());
        bean.setAvailableTickets(tickets.getValue());
        bean.setTime(LocalTime.parse(eventTime.getText()));

        new EventManager().saveEvent(bean);
        try{
            SessionManager.getInstance().setCurrentSection("Home");
            SceneManager.changeScene("/views/Home.fxml");
        }catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
