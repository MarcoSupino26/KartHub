package views;

import beans.DateBean;
import beans.OptionsBean;
import beans.SlotsBean;
import controllers.BookManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

import models.slots.TimeSlot;
import utils.BookingSession;
import utils.SessionManager;

import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private CheckBox race;
    @FXML
    private CheckBox quali;
    @FXML
    private CheckBox fp;
    @FXML
    private CheckBox medals;
    @FXML
    private CheckBox champagne;
    @FXML
    private CheckBox onBoard;
    @FXML
    private DatePicker day;

    @FXML
    public void initialize() {
        form.setVisible(false);
        optional.setVisible(false);
        String usr = SessionManager.getInstance().getLoggedUser().getUsername();
        BookingSession bookingSession = SessionManager.getInstance().getBookingSession(usr);
        if(bookingSession != null) {
            affiliates.setVisible(false);
            quali.setDisable(true); // Impedisce la selezione iniziale
            race.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // Abilita o disabilita la checkbox Qualifica in base alla selezione della Gara
                    quali.setDisable(!newValue); // Se Gara è selezionata, Qualifica è abilitata
                    if (!newValue) {
                        quali.setSelected(false); // Deseleziona Qualifica se Gara non è selezionata
                    }
                }
            });
            day.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                    if(newValue != null) {
                        LocalDate selectedDay = day.getValue();
                        DateBean dateBean = new DateBean(selectedDay);
                        new BookManager().generateSlots(dateBean);
                        List <TimeSlot> timeSlotList = bookingSession.getTrack().getTimeSlots(selectedDay);
                        updateTimeSlotsListView(timeSlotList);
                    }
                }
            });

            trackName.setText(bookingSession.getTrack().getName());
            profilePic.setImage(bookingSession.getTrack().getImage());
            form.setVisible(true);
        }
    }

    @FXML
    public void updateTimeSlotsListView(List<TimeSlot> timeSlotList) {
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
    public void confirmBooking() {
        OptionsBean optionsBean = new OptionsBean();
        optionsBean.setPersonal(Integer.parseInt(personal.getText()));
        optionsBean.setRental(Integer.parseInt(rental.getText()));
        optionsBean.setRace(race.isSelected());
        optionsBean.setQuali(quali.isSelected());
        optionsBean.setFp(fp.isSelected());
        optionsBean.setMedals(medals.isSelected());
        optionsBean.setChampagne(champagne.isSelected());
        optionsBean.setOnBoard(onBoard.isSelected());
        new BookManager().saveBooking(optionsBean);
        SceneManager.changeScene("/main.fxml");
    }
}
