package graphicalcontroller.cli;

import beans.PaymentBean;
import controllers.EventManager;
import controllers.PaymentManager;

import java.util.Scanner;

public class PaymentCLI {
    UserTopBarCLI userTopBarCLI = new UserTopBarCLI();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        EventManager eventManager = new EventManager();
        PaymentManager paymentManager = new PaymentManager();

        double ticketCost = eventManager.getTicketCost();
        int soldTickets = eventManager.getSoldTickets();
        double totalCost = ticketCost * soldTickets;

        System.out.println("-------------------------");
        System.out.println("Costo totale da pagare: €" + String.format("%.2f", totalCost));
        System.out.println("Inserisci i dati della carta per procedere al pagamento:");

        System.out.print("Nome sulla carta: ");
        String name = scanner.nextLine();

        System.out.print("Cognome sulla carta: ");
        String surname = scanner.nextLine();

        System.out.print("Numero della carta: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Data di scadenza (MM/YY): ");
        String expiryDate = scanner.nextLine();

        System.out.print("CVV: ");
        String cvv = scanner.nextLine();

        PaymentBean paymentBean = new PaymentBean();
        paymentBean.setCardName(name);
        paymentBean.setCardSurname(surname);
        paymentBean.setCardNumber(cardNumber);
        paymentBean.setExpiryMonth(expiryDate);
        paymentBean.setSecurityCode(cvv);

        System.out.println("Elaborazione del pagamento...");

        if (paymentManager.processPayment(paymentBean)) {
            System.out.println("Pagamento completato con successo! Grazie per il tuo acquisto.");
            userTopBarCLI.switchToHome();
        } else {
            System.out.println("Pagamento fallito. Riprova.");
            userTopBarCLI.switchToHome();
        }
    }
}
