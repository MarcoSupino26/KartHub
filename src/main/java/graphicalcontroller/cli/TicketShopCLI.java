package graphicalcontroller.cli;

import beans.EventCreationBean;
import controllers.EventManager;
import java.util.Scanner;

public class TicketShopCLI {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        EventManager eventManager = new EventManager();
        EventCreationBean bean = eventManager.getPaymentInfo();

        int availableTickets = bean.getAvailableTickets();
        System.out.println("-------------------------");
        System.out.println("Biglietti disponibili: " + availableTickets);
        System.out.println("Nome evento: " + bean.getEventName());
        System.out.println("Tipo di evento: " + bean.getType());
        System.out.println("Luogo: " + bean.getTrack());
        System.out.println("Data: " + bean.getDate());
        System.out.println("Orario: " + bean.getTime());
        System.out.println("Prezzo per biglietto: €" + String.format("%.2f", bean.getPrice()));

        boolean continueSelection = true;
        int selectedTickets = 0;

        while (continueSelection) {
            System.out.print("Seleziona il numero di biglietti da acquistare (0 - " + availableTickets + "): ");
            selectedTickets = scanner.nextInt();

            if (selectedTickets < 0 || selectedTickets > availableTickets) {
                System.out.println("Numero di biglietti non valido. Riprova.");
                continue;
            }

            double totalCost = updatePrice(selectedTickets, bean.getPrice());
            System.out.println("Totale da pagare: €" + String.format("%.2f", totalCost));

            System.out.print("Vuoi modificare il numero di biglietti? (s/n): ");
            String modify = scanner.next();

            if (modify.equalsIgnoreCase("N")) {
                continueSelection = false;
            }
        }

        System.out.print("Confermi l'acquisto dei biglietti? (s/n): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("s")) {
            pay(selectedTickets);
            System.out.println("Acquisto completato con successo!");
        } else {
            System.out.println("Acquisto annullato.");
        }
    }

    public double updatePrice(int selectedTickets, double price) {
        return price * selectedTickets;
    }

    public void pay(int selectedTickets) {
        new EventManager().setSoldTickets(selectedTickets);
        new PaymentCLI().start();
    }

}
