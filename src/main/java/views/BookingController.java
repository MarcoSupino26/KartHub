package views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

import models.slots.TimeSlot;
import utils.BookingSession;
import utils.SessionManager;

import javafx.scene.image.ImageView;
import java.util.List;

public class BookingController {

    @FXML
    private CheckBox check;
    @FXML
    private Group optional;
    @FXML
    private Group affiliates;
    @FXML
    private Group form;
    @FXML
    private ComboBox slots;
    @FXML
    private ComboBox format;
    @FXML
    private TextField participants;
    @FXML
    private TextField rental;
    @FXML
    private TextField personal;
    @FXML
    private Label trackName;
    @FXML
    private ImageView profilePic;

    @FXML
    public void initialize() {
        form.setVisible(false);
        optional.setVisible(false);
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        BookingSession bookingSession = SessionManager.getInstance().getBookingSession(usr);
        if(bookingSession != null) {
            affiliates.setVisible(false);
            List<TimeSlot> timeSlotList = bookingSession.getTrack().getTimeSlots();
            slots.getItems().addAll(
                    timeSlotList.stream()
                            .filter(TimeSlot::isAvailable)
                            .toList()
            );
            slots.setCellFactory(comboBoxListView -> new ListCell<TimeSlot>() {
                @Override
                protected void updateItem(TimeSlot item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText(null);
                    } else {
                        String startTime = String.format("%.2f", item.getStartTime());
                        String endTime = String.format("%.2f", item.getEndTime());
                        setText(startTime + " - " + endTime);
                    }
                }
            });
            slots.setButtonCell(new ListCell<TimeSlot>() {
                @Override
                protected void updateItem(TimeSlot item, boolean empty){
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText(null);
                    } else {
                        String startTime = String.format("%.2f", item.getStartTime());
                        String endTime = String.format("%.2f", item.getEndTime());
                        setText(startTime + " - " + endTime);
                    }
                }
            });
            trackName.setText(bookingSession.getTrack().getName());
            profilePic.setImage(bookingSession.getTrack().getImage());
            form.setVisible(true);
        }
    }

    @FXML
    public void switchToEventi(ActionEvent event){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToHome(Event event){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(Event event){
        SessionManager.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void bookTrack(Event event){
        SceneManager.changeScene("/trackchoice.fxml");
    }

    @FXML
    public void showOptions(Event event){
        boolean checked = check.isSelected();
        optional.setVisible(checked);
        optional.setManaged(checked);
    }

    @FXML
    public void proceed(Event event){
        form.setVisible(true);
    }
}
