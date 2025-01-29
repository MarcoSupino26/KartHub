package graphicalcontroller.cli;

import utils.session.SessionManager;

import java.util.Scanner;

public class HomeCLI {

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("-------------------------");
        System.out.println("Benvenuto in KartHub!");
        System.out.println("Lo strumento per gli appassionati di kart");
        loadTopBar();
    }

    private void loadTopBar() {
        String userType = (SessionManager.getInstance().getLoggedUser() != null)
                ? SessionManager.getInstance().getLoggedUser().getType()
                : "None";

        if ("Proprietario".equals(userType)) {
            OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();
            while (true) {
                ownerTopBarCLI.displayMenu();
                System.out.print("-> ");
                String choice = scanner.nextLine();
                handleOwnerChoice(choice, ownerTopBarCLI);
            }
        } else {
            UserTopBarCLI userTopBarCLI = new UserTopBarCLI();
            while (true) {
                userTopBarCLI.displayMenu();
                if(SessionManager.getInstance().getLoggedUser() != null) System.out.println("5. Vedi prenotazioni");
                System.out.print("-> ");
                String choice = scanner.nextLine();
                handleUserChoice(choice, userTopBarCLI);
            }
        }
    }

    private void handleOwnerChoice(String choice, OwnerTopBarCLI ownerTopBar) {
        switch (choice) {
            case "1":
                ownerTopBar.switchToHome();
                break;
            case "2":
                ownerTopBar.switchToManager();
                break;
            case "3":
                ownerTopBar.switchToEventi();
                break;
            case "4":
                ownerTopBar.logout();
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }

    private void handleUserChoice(String choice, UserTopBarCLI userTopBar) {
        switch (choice) {
            case "1":
                userTopBar.switchToHome();
                break;
            case "2":
                userTopBar.switchToPrenota();
                break;
            case "3":
                userTopBar.switchToEventi();
                break;
            case "4":
                userTopBar.switchToLogin();
                break;
            case "5":
                if(SessionManager.getInstance().getLoggedUser() != null) new UserBookingsCLI().start();
                else System.out.println("Opzione non valida. Riprova");
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }
}

