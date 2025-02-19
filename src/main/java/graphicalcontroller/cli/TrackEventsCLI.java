package graphicalcontroller.cli;

import beans.TrackEventBean;
import controllers.EventManager;

import java.util.List;
import java.util.Scanner;

public class TrackEventsCLI {

    private Scanner scanner = new Scanner(System.in);
    private final OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        while (true) {
            ownerTopBarCLI.displayMenu();
            displayMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println("5. Visualizza Eventi");
        System.out.println("6. Aggiungi Evento");
        System.out.print("-> ");
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
            case "5":
                displayEvents();
                break;
            case "6":
                addEvent();
                break;
            default:
                System.out.println("Opzione non valida. Riprova.");
                break;
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
