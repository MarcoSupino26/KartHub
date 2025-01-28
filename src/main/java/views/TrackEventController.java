package views;

import beans.TrackEventBean;
import controllers.EventManager;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.List;


public class TrackEventController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List <TrackEventBean> trackEventsList;
        EventManager eventManager = new EventManager();

        eventManager.startEventSession();

        VBox eventsContainer = new VBox(10);
        eventsContainer.setPrefWidth(500);
        eventsContainer.setPrefHeight(100);
        eventsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10");

        trackEventsList = eventManager.getAssociatedEvents(); //Recupero delle info degli eventi di un tracciato
        for (TrackEventBean trackEventBean : trackEventsList) {
            displayEvent(trackEventBean, eventsContainer);
        }
        scrollPane.setContent(eventsContainer);

        scrollPane.requestLayout();
    }

    public void displayEvent(TrackEventBean trackEvent, VBox eventsContainer) {//Personalizzazione delle varie HBox
        HBox displayedEvent = new HBox();
        displayedEvent.setPrefWidth(500);
        displayedEvent.setPrefHeight(100);
        displayedEvent.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-border-radius: 10; -fx-border-color: #c5151d");
        displayedEvent.setSpacing(10);
        displayedEvent.setAlignment(Pos.CENTER_LEFT);

        VBox eventDetails  = new VBox(10);
        eventDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5; -fx-border-radius: 10;");
        eventDetails.setPrefWidth(500);
        eventDetails.setPrefHeight(100);

        Text eventName = new Text(trackEvent.getTrackEventName());
        eventName.setFont(Font.font("Futura-Medium", 22));
        eventName.setFill(Color.WHITE);
        Text tickets = customize(new Text(String.format("Biglietti rimasti:%d", trackEvent.getEventTickets())));
        Text eventDate = customize(new Text(String.valueOf(trackEvent.getDay())));
        Text eventTime = customize(new Text(String.valueOf(trackEvent.getStartHour())));

        eventDetails.getChildren().addAll(eventName, tickets, eventDate, eventTime);

        displayedEvent.getChildren().addAll(eventDetails);

        eventsContainer.getChildren().add(displayedEvent);
    }

    public Text customize(Text text) {
        text.setFont(Font.font("Futura-Medium",16));
        text.setFill(Color.LIGHTGRAY);
        return text;
    }

    @FXML
    public void addEvent(){
        try{
            SceneManager.changeScene("/eventcreation.fxml");
        }catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
