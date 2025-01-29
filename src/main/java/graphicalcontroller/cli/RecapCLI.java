package graphicalcontroller.cli;

import beans.BookRecapBean;
import controllers.BookManager;
import utils.session.SessionManager;

public class RecapCLI {

    public void start() {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        BookManager bookManager = new BookManager();
        BookRecapBean recap = bookManager.getBookRecap();

        System.out.println("-------------------------");
        System.out.println(username + ", ecco la tua prenotazione:");

        System.out.println("Numero di kart a noleggio: " + recap.getRentalKarts());
        System.out.println("Numero di kart personali: " + recap.getPersonalKarts());
        System.out.println("Costo totale: €" + String.format("%.2f", recap.getBookingCost()));
        System.out.println("ID prenotazione: " + recap.getBookingID());
        System.out.println("Descrizione: " + recap.getBookingDesc());
        System.out.println("Pista prenotata: " + recap.getBookedTrack());
        System.out.println("Orario del turno: " + recap.getShift());

        bookManager.clearSession();

        new HomeCLI().start();
    }

}

