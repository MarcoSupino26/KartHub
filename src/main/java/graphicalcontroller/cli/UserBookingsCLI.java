package graphicalcontroller.cli;

import beans.BookingsDisplayBean;
import controllers.BookManager;

import java.util.List;

public class UserBookingsCLI {

    public void start() {
        displayUserBookings();
        new HomeCLI().start();
    }

    private void displayUserBookings() {
        List<BookingsDisplayBean> bookings = new BookManager().getUserBookings();

        if (bookings.isEmpty()) {
            System.out.println("Non ci sono prenotazioni da visualizzare.");
        } else {
            for (BookingsDisplayBean booking : bookings) {
                displayBookingDetails(booking);
            }
        }
    }

    private void displayBookingDetails(BookingsDisplayBean booking) {
        System.out.println("\nTracciato: " + booking.getTrackName());
        System.out.println("Giorno: " + booking.getDate());
        System.out.println("Turno: " + booking.getShift());
        System.out.println("Kart noleggiati: " + booking.getRental());
        System.out.println("Kart personali: " + booking.getPersonal());
        System.out.println("Descrizione: " + booking.getDescription());
        System.out.println("Costo: €" + String.format("%.2f", Double.parseDouble(booking.getCost())));
        System.out.println("-------------------------------------");
    }
}

