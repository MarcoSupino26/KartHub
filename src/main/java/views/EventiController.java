package views;

import beans.BookingsDisplay;
import controllers.EventManager;
import controllers.ManageController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.event.KartEvent;
import utils.EventSession;
import utils.SessionManager;

import java.util.List;

public class EventiController {
    @FXML
    private Button logIn;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        if(SessionManager.getInstance().getLoggedUser()!=null) logIn.setText("Logout");

        new EventManager().getAllEvents();

        EventSession eventSession = SessionManager.getInstance().getEventSession();
        List<KartEvent> events = eventSession.getEvents();

        VBox eventsContainer = new VBox(10);
        eventsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        eventsContainer.setPrefWidth(500);
        eventsContainer.setPrefHeight(100);

        for(KartEvent event : events) {
            displayEvents(event, eventsContainer);
        }
        scrollPane.setContent(eventsContainer);
    }

    public void displayEvents(KartEvent kartEvent, VBox eventsContainer) {
        HBox eventDisplayed = new HBox(10);
        eventDisplayed.setPrefHeight(100);
        eventDisplayed.setPrefWidth(500);
        eventDisplayed.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px");
        eventDisplayed.setSpacing(20);
        eventDisplayed.setAlignment(Pos.CENTER_LEFT);

        VBox eventDetails = new VBox(10);
        eventDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5px; -fx-border-radius: 10px");

        Text eventName = customize(new Text(kartEvent.getEventName()));
        Text eventType = customize(new Text(kartEvent.getEventType()));
        Text trackName = customize(new Text(kartEvent.getTrackName()));
        Text eventDate = customize(new Text(String.valueOf(kartEvent.getEventDate())));
        Text eventTime = customize(new Text(String.valueOf(kartEvent.getEventTime())));
        Text tickets = customize(new Text("Biglietti rimasti: " + String.valueOf(kartEvent.getTickets())));
        String ticketCost = String.format("%.2f",kartEvent.getCost());
        Text cost = customize(new Text("Costo: €" + ticketCost));

        Button buyTicket = new Button("Buy Ticket");
        buyTicket.setStyle("-fx-background-color: #c5151d; -fx-text-fill: white;");
        buyTicket.setFont(Font.font("Futura-Medium"));
        buyTicket.setOnAction(event->ticketShop(event, kartEvent));

        eventDetails.getChildren().addAll(eventName, eventType, trackName, eventDate, eventTime, tickets, cost);

        eventDisplayed.getChildren().add(eventDetails);
        eventDisplayed.getChildren().add(buyTicket);

        eventsContainer.getChildren().add(eventDisplayed);

        eventsContainer.requestLayout();
    }

    public Text customize(Text text) {
        text.setFont(Font.font("Futura-Medium", 22));
        text.setFill(Color.WHITE);
        return text;
    }

    @FXML
    public void ticketShop(ActionEvent event, KartEvent kartEvent) {
        SessionManager.getInstance().getEventSession().setCurrentKartEvent(kartEvent);
        SceneManager.changeScene("/ticketbuy.fxml");
    }

    @FXML
    public void switchToHome(MouseEvent event){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToPrenota(ActionEvent event){
        SceneManager.changeScene("/prenota.fxml");
    }

    @FXML
    public void log(){
        if(SessionManager.getInstance().getLoggedUser() != null){
            String usr = SessionManager.getInstance().getLoggedUser().getUsername();
            if(SessionManager.getInstance().getManageSession(usr) != null) SessionManager.getInstance().freeManageSession();
            SessionManager.getInstance().freeManageSession();
            SessionManager.getInstance().freeSession();
            SceneManager.changeScene("/main.fxml");
        }else SceneManager.changeScene("/login.fxml");
    }
}