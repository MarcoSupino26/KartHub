package views.cli;

import beans.CombinedSlotsBean;
import controllers.BookManager;
import beans.OptionsBean;
import beans.SlotBean;
import beans.TrackProfileBean;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidDateFormatException;

import java.time.LocalDate;
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
        boolean continueBooking = true;

        while (continueBooking) {
            showOptions();


            System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
            try {
                selectedDay = LocalDate.parse(scanner.nextLine());
                if(selectedDay == null) throw new EmptyFieldException();
                if(selectedDay.isAfter(LocalDate.now())) {
                    throw new InvalidDateException();
                }
            } catch (InvalidDateException e) {
                e.handleException();
            } catch (InvalidDateFormatException e){
                e.handleException();
            } catch (EmptyFieldException e){
                e.handleException();
            }

            updateTimeSlots();

            System.out.println("\nVuoi scegliere un altro giorno o proseguire?");
            System.out.println("1. Selezionare un altro giorno");
            System.out.println("2. Proseguire con la prenotazione");

            System.out.print("Scegli un'opzione (1/2): ");
            String option = scanner.nextLine();

            if ("2".equals(option)) {
                continueBooking = false;
                confirmBooking();
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

        try{
            if(!optionsListContains(options, "1") && !optionsListContains(options,"3")) throw new EmptyFieldException();
        } catch (EmptyFieldException e){
            e.handleException();
        }
        raceSelected = optionsListContains(options, "1");
        qualiSelected = optionsListContains(options, "2");
        fpSelected = optionsListContains(options, "3");
        medalsSelected = optionsListContains(options, "4");
        champagneSelected = optionsListContains(options, "5");
        onBoardSelected = optionsListContains(options, "6");

        /*System.out.print("Numero di persone: ");
        rentalKarts = Integer.parseInt(scanner.nextLine());
        System.out.print("Usate kart personali? (s/n): ");
        checkSelected = "s".equalsIgnoreCase(scanner.nextLine());

        if (checkSelected) {
            System.out.print("Inserisci il numero di kart personali: ");
            personalKarts = Integer.parseInt(scanner.nextLine());
            rentalKarts = rentalKarts - personalKarts;
        } else {
            personalKarts = 0;
        }*/
        try {
            System.out.print("Numero di persone: ");
            // Verifica se il campo rental è vuoto o non valido
            String rentalInput = scanner.nextLine();
            if (rentalInput.isEmpty()) {
                throw new EmptyFieldException("Il numero di persone (rental) non può essere vuoto.");
            }
            rentalKarts = Integer.parseInt(rentalInput);

            System.out.print("Usate kart personali? (s/n): ");
            checkSelected = "s".equalsIgnoreCase(scanner.nextLine());

            if (checkSelected) {
                System.out.print("Inserisci il numero di kart personali: ");
                // Verifica se il campo personal è vuoto o non valido
                String personalInput = scanner.nextLine();
                if (personalInput.isEmpty()) {
                    throw new EmptyFieldException("Il numero di kart personali non può essere vuoto.");
                }
                personalKarts = Integer.parseInt(personalInput);
                rentalKarts = rentalKarts - personalKarts;
            } else {
                personalKarts = 0;
            }

        } catch (EmptyFieldException e) {
            e.handleException(); // Gestisce l'eccezione a seconda che sia CLI o GUI
        }
    }

    private boolean optionsListContains(String[] options, String option) {
        for (String o : options) {
            if (o.trim().equals(option)) {
                return true;
            }
        }
        return false;
    }

    private void updateTimeSlots() {
        BookManager bookManager = new BookManager();
        bookManager.generateSlots2(selectedDay);

        List<SlotBean> slotsList = bookManager.getSlots2(selectedDay);
        if (slotsList.isEmpty()) {
            System.out.println("Nessuno slot disponibile");
        }else updateTimeSlotsList(slotsList, bookManager);
    }

    private void updateTimeSlotsList(List<SlotBean> slotsList, BookManager bookManager) {
        slotsList = updateCombinedSlots(slotsList, bookManager);
        populateSlotsListView(slotsList);
    }

    private List<SlotBean> updateCombinedSlots(List<SlotBean>slotsList, BookManager bookManager) {
        if(raceSelected) {
            CombinedSlotsBean combinedSlots = new CombinedSlotsBean(slotsList, true, qualiSelected, fpSelected);
            slotsList = bookManager.getCombinedSlots2(combinedSlots);
        }
        return slotsList;
    }

    private void populateSlotsListView(List<SlotBean> slotsList) {
        System.out.println("Slot disponibili:");

        int i = 1;
        for (SlotBean slot : slotsList) {
            if (slot.isFree()) {
                String formattedSlot = String.format("%d: %.2f - %.2f", i, slot.getSlotStart(), slot.getSlotEnd()).replace(",", ".");
                System.out.println(formattedSlot);
                i++;
            }
        }
        System.out.print("Seleziona un turno: ");
        int selectedIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (selectedIndex >= 0 && selectedIndex < slotsList.size()) {
            SlotBean selected = slotsList.get(selectedIndex);
            selectedSlot = String.format("%.2f - %.2f", selected.getSlotStart(), selected.getSlotEnd());
        } else {
            System.out.println("Scelta non valida, riprova.");
            populateSlotsListView(slotsList);
        }
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
        String startTime = parts[0].replace(",",".");
        optionsBean.setStartTime(Double.parseDouble(startTime));
        optionsBean.setShifts(selectedSlot);

        new BookManager().saveBooking(optionsBean);

        new RecapCLI().start();
    }
}