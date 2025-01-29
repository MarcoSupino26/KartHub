package graphicalcontroller.cli;

import beans.CombinedSlotsBean;
import controllers.BookManager;
import beans.OptionsBean;
import beans.SlotBean;
import beans.TrackProfileBean;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BookingCLI {

    private static final Scanner scanner = new Scanner(System.in);

    private boolean raceSelected;
    private boolean qualiSelected;
    private boolean fpSelected;
    private boolean medalsSelected;
    private boolean champagneSelected;
    private boolean onBoardSelected;
    private boolean checkSelected;
    private int rentalKarts;
    private int personalKarts;
    private LocalDate selectedDay;
    private String selectedSlot;

    public void start() {
        BookManager bookManager = new BookManager();
        TrackProfileBean selectedTrack = bookManager.getSelectedTrack();

        System.out.println("Tracciato selezionato: " + selectedTrack.getName());

        while (true) {
            showOptions();
            selectDate();
            updateTimeSlots();

            System.out.println("\nVuoi scegliere un altro giorno o proseguire?");
            System.out.println("1. Selezionare un altro giorno");
            System.out.println("2. Proseguire con la prenotazione");

            System.out.print("Scegli un'opzione (1/2): ");
            String option = scanner.nextLine();

            if ("2".equals(option)) {
                confirmBooking();
                break;
            } else if (!"1".equals(option)) {
                System.out.println("Scelta non valida");
            }
        }
    }

    private void showOptions() {
        System.out.println("-------------------------");
        System.out.print("Seleziona le opzioni:\n1. Gara\n2. Qualifiche (non selezionabile senza gara)\n3. Prova Libera\n4. Medaglie\n5. Champagne\n6. On-Board\n");
        System.out.print("Seleziona le opzioni (separate da virgola): ");
        String[] options = scanner.nextLine().split(",");

        try {
            validateOptions(options);
        } catch (EmptyFieldException e) {
            e.handleException();
            showOptions(); // Riprova in caso di errore
            return;
        }

        raceSelected = optionsListContains(options, "1");
        qualiSelected = optionsListContains(options, "2");
        fpSelected = optionsListContains(options, "3");
        medalsSelected = optionsListContains(options, "4");
        champagneSelected = optionsListContains(options, "5");
        onBoardSelected = optionsListContains(options, "6");

        collectKartDetails();
    }

    private void validateOptions(String[] options) throws EmptyFieldException {
        if (!optionsListContains(options, "1") && !optionsListContains(options, "3")) {
            throw new EmptyFieldException("Devi selezionare almeno Gara o Prova Libera.");
        }
    }

    private void collectKartDetails() {
        try {
            System.out.print("Numero di persone: ");
            rentalKarts = readInteger();

            System.out.print("Usate kart personali? (s/n): ");
            checkSelected = "s".equalsIgnoreCase(scanner.nextLine());

            if (checkSelected) {
                System.out.print("Inserisci il numero di kart personali: ");
                personalKarts = readInteger();
                rentalKarts -= personalKarts;
            } else {
                personalKarts = 0;
            }
        } catch (EmptyFieldException e) {
            e.handleException();
            collectKartDetails();
        }
    }

    private void selectDate() {
        System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
        try {
            selectedDay = parseDate(scanner.nextLine());
        } catch (InvalidDateException e){
            e.handleException();
            selectDate();
        }catch (InvalidDateFormatException e) {
            e.handleException();
            selectDate();
        }
    }

    private LocalDate parseDate(String input) throws InvalidDateException, InvalidDateFormatException {
        if (input.isEmpty()) {
            throw new InvalidDateFormatException();
        }

        try {
            LocalDate date = LocalDate.parse(input);
            if (date.isBefore(LocalDate.now())) {
                throw new InvalidDateException();
            }
            return date;
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    private void updateTimeSlots() {
        BookManager bookManager = new BookManager();
        bookManager.generateSlots(selectedDay);

        List<SlotBean> slotsList = bookManager.getSlots(selectedDay);
        if (slotsList.isEmpty()) {
            System.out.println("Nessuno slot disponibile");
        } else {
            slotsList = updateCombinedSlots(slotsList, bookManager);
            populateSlotsListView(slotsList);
        }
    }

    private List<SlotBean> updateCombinedSlots(List<SlotBean> slotsList, BookManager bookManager) {
        if (raceSelected) {
            CombinedSlotsBean combinedSlots = new CombinedSlotsBean(slotsList, true, qualiSelected, fpSelected);
            return bookManager.getCombinedSlots(combinedSlots);
        }
        return slotsList;
    }

    private void populateSlotsListView(List<SlotBean> slotsList) {
        System.out.println("Slot disponibili:");

        for (int i = 0; i < slotsList.size(); i++) {
            SlotBean slot = slotsList.get(i);
            if (slot.isFree()) {
                System.out.printf("%d: %.2f - %.2f%n", i + 1, slot.getSlotStart(), slot.getSlotEnd());
            }
        }

        System.out.print("Seleziona un turno: ");
        int selectedIndex = readInteger() - 1;

        if (selectedIndex >= 0 && selectedIndex < slotsList.size()) {
            SlotBean selected = slotsList.get(selectedIndex);
            selectedSlot = String.format("%.2f - %.2f", selected.getSlotStart(), selected.getSlotEnd());
        } else {
            System.out.println("Scelta non valida, riprova.");
            populateSlotsListView(slotsList);
        }
    }

    private int readInteger() throws EmptyFieldException {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            throw new EmptyFieldException("Il campo non può essere vuoto.");
        }
        return Integer.parseInt(input);
    }

    private void confirmBooking() {
        OptionsBean optionsBean = new OptionsBean();
        optionsBean.setRental(rentalKarts);
        optionsBean.setPersonal(personalKarts);
        optionsBean.setRace(raceSelected);
        optionsBean.setQuali(qualiSelected);
        optionsBean.setFp(fpSelected);
        optionsBean.setMedals(medalsSelected);
        optionsBean.setChampagne(champagneSelected);
        optionsBean.setOnBoard(onBoardSelected);
        optionsBean.setDate(selectedDay);

        String[] parts = selectedSlot.split("-");
        optionsBean.setStartTime(Double.parseDouble(parts[0].replace(",", ".")));
        optionsBean.setShifts(selectedSlot);

        new BookManager().saveBooking(optionsBean);

        new RecapCLI().start();
    }

    private boolean optionsListContains(String[] options, String option) {
        for (String o : options) {
            if (o.trim().equals(option)) {
                return true;
            }
        }
        return false;
    }
}
