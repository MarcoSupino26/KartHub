package views.cli;

import beans.TrackProfileBean;
import controllers.ManageController;
import java.util.Scanner;

public class TrackManagerCLI {

    private Scanner scanner = new Scanner(System.in);
    private OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        displayTrackInfo();

        ownerTopBarCLI.displayMenu();

        displayTrackManagerMenu();
        String choice = scanner.nextLine();
        handleChoice(choice);
    }

    private void displayTrackManagerMenu() {
        System.out.println("5. Visualizza prenotazioni");
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
                seeBookings();
                break;
            default:
                System.out.println("Opzione non valida. Riprova.");
                break;
        }
    }

    private void displayTrackInfo() {
        TrackProfileBean trackProfileBean = new ManageController().getTrackInfo();
        System.out.println("Nome del tracciato: " + trackProfileBean.getName());
    }

    private void seeBookings() {
        new TrackBookingsCLI().start();
    }
}
