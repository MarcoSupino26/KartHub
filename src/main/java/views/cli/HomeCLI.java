package views.cli;

import utils.SessionManager;

import java.util.Scanner;

public class HomeCLI {

    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        System.out.println("Benvenuto in KartHub!");
        System.out.println("Lo strumento per gli appassionati di kart");
        loadTopBar();
    }

    private void loadTopBar() {
        String userType = (SessionManager.getInstance().getLoggedUser() != null)
                ? SessionManager.getInstance().getLoggedUser().getType()
                : "Nessuno";

        System.out.println("Tipo utente: " + userType);

        if ("Proprietario".equals(userType)) {
            OwnerTopBarCLI ownerTopBar = new OwnerTopBarCLI();
            ownerTopBar.displayMenu();
            System.out.print("-> ");
            String choice = scanner.nextLine();
            handleOwnerChoice(choice);
        } else {
            UserTopBarCLI userTopBar = new UserTopBarCLI();
            System.out.print("-> ");
            String choice = scanner.nextLine();
            userTopBar.displayMenu();
        }
    }

    private void handleOwnerChoice(String choice) {
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
}

