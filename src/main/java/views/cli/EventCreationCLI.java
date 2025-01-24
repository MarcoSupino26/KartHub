package views.cli;

import beans.EventCreationBean;
import controllers.EventManager;
import java.time.LocalTime;
import java.util.Scanner;

public class EventCreationCLI {
    private String eventName;
    private String eventType;
    private double ticketCost;
    private LocalTime eventTime;
    private java.time.LocalDate eventDate;
    private int availableTickets;


    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        createEvent();
    }

    private void createEvent() {
        System.out.print("Inserisci il nome dell'evento: ");
        eventName = scanner.nextLine();
        System.out.print("Inserisci il tipo di evento: ");
        eventType = scanner.nextLine();
        System.out.print("Inserisci il costo del biglietto: ");
        ticketCost = Double.parseDouble(scanner.nextLine());
        System.out.print("Inserisci l'orario dell'evento (HH:mm): ");
        eventTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Inserisci la data dell'evento (yyyy-MM-dd): ");
        eventDate = java.time.LocalDate.parse(scanner.nextLine());
        System.out.print("Inserisci il numero di biglietti disponibili: ");
        availableTickets = Integer.parseInt(scanner.nextLine());

        saveEvent();

        ownerTopBarCLI.displayMenu();
        String choice = scanner.nextLine();

        handleChoice(choice);
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

    private void saveEvent() {
        EventCreationBean bean = new EventCreationBean(eventName);
        bean.setDate(eventDate);
        bean.setPrice(ticketCost);
        bean.setType(eventType);
        bean.setAvailableTickets(availableTickets);
        bean.setTime(eventTime);

        EventManager eventManager = new EventManager();
        eventManager.saveEvent(bean);

        System.out.println("Evento creato con successo!");

        ownerTopBarCLI.displayMenu();
    }
}
