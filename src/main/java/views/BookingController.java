package views;

import beans.*;
import controllers.BookManager;
import exceptions.DataLoadException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidDateFormatException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

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
    private ComboBox<String> slots;
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

            race.selectedProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // Abilita o disabilita la checkbox Qualifica in base alla selezione della Gara
                    quali.setDisable(!newValue); //La qualifica è abilitata solo se la gara è selezionata
                    if (!newValue) {
                        quali.setSelected(false); //La qualifica viene deselezionata se la gara non è selezionata
                    }
                }
            });

            ChangeListener<Boolean> optionsListener = (observable, oldValue, newValue) -> {
                boolean selected = race.isSelected() || quali.isSelected() || fp.isSelected();
                day.setDisable(!selected); //Se nessun formato è selezionato, non è possibile selezionare il giorno
                if(!selected) day.setValue(null); //Viene deselezionato il giorno per evitare l'aggiornamento degli slot
                if(day.getValue() != null) updateTimeSlots(); //Aggiornamento dei turni se viene selezionato un giorno
            };

            //Se cambia la selezione dei formati vengono generati nuovi turni
            race.selectedProperty().addListener(optionsListener);
            quali.selectedProperty().addListener(optionsListener);
            fp.selectedProperty().addListener(optionsListener);

            day.valueProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                    if(newValue != null) updateTimeSlots(); //Se cambia il giorno selezionato vengono generati nuovamente i turni
                }
            });

            //Viene settato il form per la prenotazione
            TrackProfileBean selectedTrack = bookManager.getSelectedTrack();
            trackName.setText(selectedTrack.getName());
            profilePic.setImage(selectedTrack.getImage());
            form.setVisible(true);
        }
    }

    @FXML
    public void updateTimeSlots(){//Aggiornamento dei turni
        LocalDate selectedDay = day.getValue();
        BookManager bookManager = new BookManager();
        bookManager.generateSlots(selectedDay); //Gli slot vengono generati

        List<SlotBean> slotsList = bookManager.getSlots(selectedDay);
        updateTimeSlotsListView(slotsList, bookManager); //Aggiornamento degli slot mostrati all'utente
    }

    @FXML
    public void updateTimeSlotsListView(List<SlotBean> slotsList, BookManager bM) {
        slotsList = updateCombinedSlots(slotsList, bM);
        populateSlotsListView(slotsList);
        setupSlotsCellFactory();
    }


    private List<SlotBean> updateCombinedSlots(List<SlotBean> slotsList, BookManager bM) {
        boolean raceOption = race.isSelected();
        boolean qualiOption = quali.isSelected();
        boolean fpOption = fp.isSelected();
        CombinedSlotsBean combinedSlotsBean = new CombinedSlotsBean(slotsList, raceOption, qualiOption, fpOption);
        slotsList = bM.getCombinedSlots(combinedSlotsBean);
        return slotsList;
        //Gli slot continuano a essere singoli, ma vengono mostrati all'utente come un semplice turno.
        //La gara occupa 2 slot, la qualifica e le prove libere 1 slot
    }

    private void populateSlotsListView(List<SlotBean> slotsList) {
        slots.getItems().clear();
        for (SlotBean slot : slotsList) {
            if (slot.isFree()) {
                String formattedSlot = String.format("%.2f - %.2f", slot.getSlotStart(), slot.getSlotEnd()).replace(",", ".");
                slots.getItems().add(formattedSlot);
            }
        }
    }

    private void setupSlotsCellFactory() {
        slots.setCellFactory(comboBoxListView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item);
            }
        });

        slots.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item);
            }
        });
    }

    @FXML
    public void bookTrack(){
        try {
            SceneManager.changeScene("/TrackChoice.fxml");
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void showOptions(){
        boolean checked = check.isSelected();
        optional.setVisible(checked);
        optional.setManaged(checked);
    }

    @FXML
    public void bookedTracks(){
        try{
            SceneManager.changeScene("/UserBookings.fxml");
        }catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void confirmBooking() {
        OptionsBean optionsBean = new OptionsBean();
        int rentalKarts = 0;
        int personalKarts = 0;

        try {
            if (rental.getText().isEmpty()) {
                throw new EmptyFieldException("Il campo 'rental' non può essere vuoto.");
            }
            rentalKarts = Integer.parseInt(rental.getText());

            if (check.isSelected()) {
                if (personal.getText().isEmpty()) {
                    throw new EmptyFieldException("Il campo 'personal' non può essere vuoto quando l'opzione è selezionata.");
                }
                personalKarts = Integer.parseInt(personal.getText());
                rentalKarts = rentalKarts - personalKarts;
                optionsBean.setPersonal(personalKarts);
            } else {
                optionsBean.setPersonal(0);
            }

            if (!race.isSelected() && !fp.isSelected()) {
                throw new EmptyFieldException("Devi selezionare almeno una tra 'race' o 'fp'.");
            }

            optionsBean.setRental(rentalKarts);
            optionsBean.setRace(race.isSelected());
            optionsBean.setQuali(quali.isSelected());
            optionsBean.setFp(fp.isSelected());
            optionsBean.setMedals(medals.isSelected());
            optionsBean.setChampagne(champagne.isSelected());
            optionsBean.setOnBoard(onBoard.isSelected());

        } catch (EmptyFieldException e) {
            e.handleException();
        }


        try {
            if(day.getValue() == null) {
                throw new EmptyFieldException();
            }
            if(day.getValue().isBefore(LocalDate.now())) {
                throw new InvalidDateException();
            }
        } catch (InvalidDateException e) {
            e.handleException();
        } catch (InvalidDateFormatException e){
            e.handleException();
        }catch (EmptyFieldException e){
            e.handleException();
        }
        optionsBean.setDate(day.getValue());

        String slot = slots.getValue();
        String[] parts = slot.split("-");

        optionsBean.setStartTime((Double.parseDouble(parts[0])));
        optionsBean.setShifts(slot);
        new BookManager().saveBooking(optionsBean);
        try {
            SceneManager.changeScene("/BookingRecap.fxml");
        } catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
