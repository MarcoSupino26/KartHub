package graphicalcontroller.cli;

import beans.UserEventsBean;
import controllers.EventManager;
import java.util.List;
import java.util.Scanner;

public class UserEventsCLI {

    UserTopBarCLI userTopBarCLI = new UserTopBarCLI();

    Scanner scanner = new Scanner(System.in);
    public void start() {
        while (true) {
            userTopBarCLI.displayMenu();
            displayMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println("5. Acquista biglietto");
        System.out.print("-> ");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                userTopBarCLI.switchToHome();
                break;
            case "2":
                userTopBarCLI.switchToPrenota();
                break;
            case "3":
                userTopBarCLI.switchToEventi();
                break;
            case "4":
                userTopBarCLI.switchToLogin();
                break;
            case "5":
                selectEvent();
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }

    public void displayEvent(UserEventsBean bean, int index) {
        System.out.println("Evento #" + index);
        System.out.println("Nome dell'evento: " + bean.getEvent());
        System.out.println("Tipo di evento: " + bean.getType());
        System.out.println("Luogo: " + bean.getPlace());
        System.out.println("Data: " + bean.getEventDay());
        System.out.println("Orario di inizio: " + bean.getEventStart());
        System.out.println("Biglietti rimasti: " + bean.getRemainingTickets());
        System.out.println("Costo biglietto: €" + String.format("%.2f", bean.getTicketPrice()));
        System.out.println("-------------------------");
    }

    public void ticketShop(UserEventsBean bean) {
        System.out.println("Hai scelto l'evento: " + bean.getEvent());
        System.out.println("Prezzo biglietto: €" + String.format("%.2f", bean.getTicketPrice()));
        new EventManager().setCurrentEvent(bean);
        new TicketShopCLI().start();
    }

    private void selectEvent() {
        EventManager eventManager = new EventManager();
        eventManager.startEventSession();
        List<UserEventsBean> events = eventManager.getAllEvents();

        for (int i = 0; i < events.size(); i++) {
            displayEvent(events.get(i), i + 1);
        }

        System.out.print("Scegli un evento per acquistare i biglietti (inserisci il numero dell'evento): ");
        int selectedEventIndex = scanner.nextInt() - 1;

        if (selectedEventIndex >= 0 && selectedEventIndex < events.size()) {
            UserEventsBean selectedEvent = events.get(selectedEventIndex);
            ticketShop(selectedEvent);
        } else {
            System.out.println("Evento non valido.");
        }
        scanner.close();
    }
}
