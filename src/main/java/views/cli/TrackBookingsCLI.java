package views.cli;

import beans.BookingsDisplayBean;
import controllers.ManageController;

import java.util.List;
import java.util.Scanner;

public class TrackBookingsCLI {

    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();
        displayBookings();
        while(true) {
            ownerTopBarCLI.displayMenu();
            System.out.print("\n-> ");
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    private void displayBookings() {
        List<BookingsDisplayBean> bookings = new ManageController().getBookings();

        if (bookings.isEmpty()) {
            System.out.println("Non ci sono prenotazioni da visualizzare.");
        } else {
            for (BookingsDisplayBean booking : bookings) {
                displayBookingDetails(booking);
            }
        }
    }

    private void displayBookingDetails(BookingsDisplayBean booking) {
        System.out.println("\nPrenotazione di " + booking.getUsr());
        System.out.println("Turno: " + booking.getShift());
        System.out.println("Kart noleggiati: " + booking.getRental());
        System.out.println("Kart personali: " + booking.getPersonal());
        System.out.println("Descrizione: " + booking.getDescription());
        System.out.println("Incasso: €" + String.format("%.2f", Double.parseDouble(booking.getCost())));
        System.out.println("-------------------------------------");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                ownerTopBarCLI.switchToHome();
                break;
            case "2":
                ownerTopBarCLI.switchToManager();
                break;
            case "3":
                ownerTopBarCLI.switchToEventi();
                break;
            case "4":
                ownerTopBarCLI.logout();
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }
}

