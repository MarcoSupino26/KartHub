package views;

import beans.EventBean;
import controllers.EventManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.EventSession;
import utils.SessionManager;
import java.util.List;

import java.util.ArrayList;

public class TrackEventController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List <EventBean> eventBeanList = new ArrayList<>();
        EventManager eventManager = new EventManager();
        EventSession eventSession = SessionManager.getInstance().getEventSession();

        VBox eventsContainer = new VBox(10);
        eventsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10;");

        eventBeanList = eventManager.getAssociatedEvents(eventSession.getTrackname());
        for (EventBean eventBean : eventBeanList) {
            displayEvent(eventBean, eventsContainer);
        }
        scrollPane.setContent(eventsContainer);
    }

    public void displayEvent(EventBean eventBean, VBox eventsContainer) {
        HBox displayedEvent = new HBox(10);
        displayedEvent.setPrefWidth(500);
        displayedEvent.setPrefHeight(100);
        displayedEvent.setSpacing(10);
        displayedEvent.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-border-radius: 10");
        displayedEvent.setAlignment(Pos.CENTER_LEFT);

        VBox eventDetails  = new VBox(10);
        eventDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5; -fx-border-radius: 10;");

        Text eventName = customize(new Text(eventBean.getEventName()));
        Text tickets = customize(new Text("Biglietti rimasti:" + String.valueOf(eventBean.getTickets())));
        Text eventDate = customize(new Text(String.valueOf(eventBean.getEventDate())));
        Text eventTime = customize(new Text(String.valueOf(eventBean.getEventTime())));

        eventDetails.getChildren().addAll(eventName, tickets, eventDate, eventTime);

        displayedEvent.getChildren().addAll(eventDetails);

        eventsContainer.getChildren().add(displayedEvent);
        eventsContainer.requestLayout();
    }

    public Text customize(Text text) {
        text.setStyle("-fx-background-color: #000000; -fx-font-size:18px; -fx-fill: white");
        text.setFont(Font.font("Futura-Medium"));
        return text;
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
    public void switchToManager(){
        SceneManager.changeScene("/trackmanger.fxml");
    }

    @FXML
    public void addEvent(){
        System.out.println("Working on it");
    }
}
