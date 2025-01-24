package views.cli;

import beans.TrackEventBean;
import controllers.EventManager;
import utils.SessionManager;

import java.util.List;
import java.util.Scanner;

public class TrackEventsCLI {

    private Scanner scanner = new Scanner(System.in);
    private OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        displayMenu();
    }

    private void displayMenu() {
        System.out.println("5. Visualizza Eventi");
        System.out.println("6. Aggiungi Evento");
        System.out.print("-> ");

        String choice = scanner.nextLine();
        handleChoice(choice);
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                ownerTopBarCLI.switchToHome();
            case "2":
                ownerTopBarCLI.switchToManager();
            case "3":
                ownerTopBarCLI.switchToEventi();
            case "4":
                ownerTopBarCLI.logout();
            case "5":
                displayEvents();
                break;
            case "6":
                addEvent();
                break;
            default:
                System.out.println("Opzione non valida. Riprova.");
        }
    }

    private void displayEvents() {
        EventManager eventManager = new EventManager();
        eventManager.startEventSession();

        List<TrackEventBean> trackEventsList = eventManager.getAssociatedEvents();
        if (trackEventsList.isEmpty()) {
            System.out.println("Non ci sono eventi disponibili al momento.");
        } else {
            System.out.println("\nLista Eventi:");
            for (TrackEventBean trackEvent : trackEventsList) {
                System.out.println("Nome Evento: " + trackEvent.getTrackEventName());
                System.out.println("Biglietti rimasti: " + trackEvent.getEventTickets());
                System.out.println("Data: " + trackEvent.getDay());
                System.out.println("Orario di inizio: " + trackEvent.getStartHour());
                System.out.println("-----------------------------------");
            }
        }
    }

    private void addEvent() {
        new EventCreationCLI().start();
    }
}
