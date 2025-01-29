package graphicalcontroller.cli;

import beans.EventCreationBean;
import controllers.EventManager;
import exceptions.InvalidDateException;
import exceptions.InvalidDateFormatException;

import java.time.LocalDate;
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
        System.out.println("-------------------------");
        System.out.print("Inserisci il nome dell'evento: ");
        eventName = scanner.nextLine();
        System.out.print("Inserisci il tipo di evento: ");
        eventType = scanner.nextLine();
        System.out.print("Inserisci il costo del biglietto: ");
        ticketCost = Double.parseDouble(scanner.nextLine());
        System.out.print("Inserisci l'orario dell'evento (HH:MM): ");
        eventTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Inserisci la data dell'evento (YYYY-MM-DD): ");
        eventDate = java.time.LocalDate.parse(scanner.nextLine());

        try {
            if(eventDate.isAfter(LocalDate.now())) {
                throw new InvalidDateException();
            }
        } catch (InvalidDateException e) {
            e.handleException();
        } catch (InvalidDateFormatException e){
            e.handleException();
        }

        System.out.print("Inserisci il numero di biglietti disponibili: ");
        availableTickets = Integer.parseInt(scanner.nextLine());

        saveEvent();

        new HomeCLI().start();
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
    }
}
