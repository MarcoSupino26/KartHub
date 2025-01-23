package views;

import beans.UserEventsBean;
import controllers.EventManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.SessionManager;

import java.util.List;

public class UserEventsController {
    @FXML
    private Button logIn;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        EventManager eventManager = new EventManager();
        eventManager.startEventSession();
        List <UserEventsBean> events = eventManager.getAllEvents();

        VBox eventsContainer = new VBox(10);
        eventsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10px");
        eventsContainer.setPrefWidth(500);
        eventsContainer.setPrefHeight(100);

        for(UserEventsBean eventBean : events) {
            displayEvents(eventBean, eventsContainer);
        }
        scrollPane.setContent(eventsContainer);
    }

    public void displayEvents(UserEventsBean bean, VBox eventsContainer) {
        HBox eventDisplayed = new HBox();
        eventDisplayed.setPrefHeight(100);
        eventDisplayed.setPrefWidth(500);
        eventDisplayed.setStyle("-fx-background-color: #000000; -fx-padding: 10px; -fx-border-radius: 10px; -fx-border-color: #c5151d");
        eventDisplayed.setSpacing(10);
        eventDisplayed.setAlignment(Pos.CENTER_LEFT);

        VBox eventDetails = new VBox(10);
        eventDetails.setStyle("-fx-background-color: #000000; -fx-padding: 5px; -fx-border-radius: 10px");
        eventDetails.setPrefWidth(500);
        eventDetails.setPrefHeight(100);

        Text eventName = new Text(bean.getEvent());
        eventName.setFont(Font.font("Futura-Medium", 22));
        eventName.setFill(Color.WHITE);
        Text eventType = customize(new Text(bean.getType()));
        Text trackName = customize(new Text(bean.getPlace()));
        Text eventDate = customize(new Text(String.valueOf(bean.getEventDay())));
        Text eventTime = customize(new Text(String.valueOf(bean.getEventStart())));
        Text tickets = customize(new Text("Biglietti rimasti: " + String.valueOf(bean.getRemainingTickets())));
        String ticketCost = String.format("%.2f",bean.getTicketPrice());
        Text cost = customize(new Text("Costo: €" + ticketCost));

        Button buyTicket = new Button("Buy Ticket");
        buyTicket.setStyle("-fx-background-color: #c5151d; -fx-text-fill: white;");
        buyTicket.setFont(Font.font("Futura-Medium", 18));
        buyTicket.setOnAction(event->ticketShop(event, bean));

        eventDetails.getChildren().addAll(eventName, eventType, trackName, eventDate, eventTime, tickets, cost);

        eventDisplayed.getChildren().add(eventDetails);
        eventDisplayed.getChildren().add(buyTicket);

        eventsContainer.getChildren().add(eventDisplayed);

        eventsContainer.requestLayout();
    }

    public Text customize(Text text) {
        text.setFont(Font.font("Futura-Medium", 16));
        text.setFill(Color.LIGHTGRAY);
        return text;
    }

    @FXML
    public void ticketShop(ActionEvent event, UserEventsBean bean) {
        new EventManager().setCurrentEvent(bean);
        SceneManager.changeScene("/ticketbuy.fxml");
    }
}