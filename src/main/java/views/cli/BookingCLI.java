package views.cli;

import controllers.BookManager;
import beans.OptionsBean;
import beans.SlotBean;
import beans.TrackProfileBean;
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
            System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
            selectedDay = LocalDate.parse(scanner.nextLine());

            updateTimeSlots();

            System.out.println("\nVuoi scegliere un altro giorno o proseguire?");
            System.out.println("1. Selezionare un altro giorno");
            System.out.println("2. Proseguire con la registrazione");

            System.out.print("Scegli un'opzione (1/2): ");
            String option = scanner.nextLine();

            if ("2".equals(option)) {
                continueBooking = false;
                showOptions();
            } else if (!"1".equals(option)) {
                System.out.println("Scelta non valida");
            }
        }
    }


    private void showOptions() {
        System.out.println("La qualifica non può essere selezionata senza la gara.");
        System.out.print("Seleziona le opzioni:\n1. Gara\n2. Qualifiche\n3. Prova Libera\n4. Medaglie\n5. Champagne\n6. On-Board\n");
        System.out.print("Seleziona le opzioni (separate da virgola): ");
        String[] options = scanner.nextLine().split(",");

        raceSelected = optionsListContains(options, "1");
        qualiSelected = optionsListContains(options, "2");
        fpSelected = optionsListContains(options, "3");
        medalsSelected = optionsListContains(options, "4");
        champagneSelected = optionsListContains(options, "5");
        onBoardSelected = optionsListContains(options, "6");

        System.out.print("Usate kart personali? (s/n): ");
        checkSelected = "s".equalsIgnoreCase(scanner.nextLine());

        if (checkSelected) {
            System.out.print("Inserisci il numero di kart a noleggio: ");
            rentalKarts = Integer.parseInt(scanner.nextLine());

            System.out.print("Inserisci il numero di kart personali: ");
            personalKarts = Integer.parseInt(scanner.nextLine());
        } else {
            rentalKarts = Integer.parseInt(scanner.nextLine());
            personalKarts = 0;
        }
        confirmBooking();
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
        populateSlotsListView(slotsList);
    }

    private void populateSlotsListView(List<SlotBean> slotsList) {
        System.out.println("Slot disponibili:");

        for (SlotBean slot : slotsList) {
            if (slot.isFree()) {
                String formattedSlot = String.format("%.2f - %.2f", slot.getSlotStart(), slot.getSlotEnd()).replace(",", ".");
                System.out.println(formattedSlot);
            }
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
        optionsBean.setStartTime(Double.parseDouble(parts[0]));
        optionsBean.setShifts(selectedSlot);

        new BookManager().saveBooking(optionsBean);

        new RecapCLI().start();
    }
}
