package views;

import beans.*;
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
        BookManager bookManager = new BookManager();
        if(bookManager.isSessionOpen()) {
            affiliates.setVisible(false);
            day.setDisable(true);
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

            ChangeListener<Boolean> optionsListener = (observable, oldValue, newValue) -> {
                boolean selected = race.isSelected() || quali.isSelected() || fp.isSelected();
                day.setDisable(!selected);
                if(day.getValue() != null) updateTimeSlots();
            };

            race.selectedProperty().addListener(optionsListener);
            quali.selectedProperty().addListener(optionsListener);
            fp.selectedProperty().addListener(optionsListener);

            day.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                    if(newValue != null) updateTimeSlots();
                }
            });

            TrackProfileBean selectedTrack = bookManager.getSelectedTrack();
            trackName.setText(selectedTrack.getName());
            profilePic.setImage(selectedTrack.getImage());
            form.setVisible(true);
        }
    }

    @FXML
    public void updateTimeSlots() {
        LocalDate selectedDay = day.getValue();
        DateBean dateBean = new DateBean(selectedDay);
        BookManager bM = new  BookManager();
        bM.generateSlots(dateBean);
        SlotsBean slotsBean = bM.getSlots(selectedDay);
        List <TimeSlot> timeSlotList = slotsBean.getTimeSlots();
        updateTimeSlotsListView(timeSlotList, bM);
    }

    @FXML
    public void updateTimeSlotsListView(List<TimeSlot> timeSlotList, BookManager bM) {
        boolean fpOption = false;
        boolean qualiOption = false;
        boolean raceOption = false;

        if(race.isSelected()){
            if(quali.isSelected()){
                qualiOption = true;
            }
            if(fp.isSelected()){
                fpOption = true;
            }
            raceOption = true;
            CombineSlotsBean combineSlotsBean = new CombineSlotsBean(timeSlotList, raceOption, qualiOption, fpOption);
            SlotsBean updatedSlots = bM.getCombinedSlots(combineSlotsBean);
            timeSlotList = updatedSlots.getTimeSlots();
        }
        slots.getItems().clear();
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
    public void bookTrack(Event event){
        SceneManager.changeScene("/trackChoice.fxml");
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
        optionsBean.setDate(day.getValue());

        TimeSlot selectedSlot = (TimeSlot) slots.getValue();
        String shift = String.format("%.2f - %.2f", selectedSlot.getStartTime(), selectedSlot.getEndTime());

        optionsBean.setStartTime(selectedSlot.getStartTime());
        System.out.println(selectedSlot.getStartTime());
        optionsBean.setShifts(shift);
        new BookManager().saveBooking(optionsBean);
        SceneManager.changeScene("/bookingrecap.fxml");
    }
}
