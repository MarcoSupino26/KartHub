package graphicalcontroller.cli;

import beans.TrackBean;
import controllers.ManageController;
import javafx.scene.image.Image;
import java.util.Scanner;

public class TrackCreationCLI {

    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    public void start() {
        while (true) {
            ownerTopBarCLI.displayMenu();
            displayMenu();
            String choice  = scanner.nextLine();
            handleChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println("5. Inserisci tracciato");
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
                setRegistration();
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }

    private void setRegistration() {
        System.out.print("Nome del tracciato: ");
        String trackName = scanner.nextLine();

        System.out.print("Descrizione del tracciato: ");
        String description = scanner.nextLine();

        System.out.print("Indirizzo del tracciato: ");
        String address = scanner.nextLine();

        System.out.print("Scegli il percorso dell'immagine per il tracciato (image//path): ");
        String imagePath = scanner.nextLine();
        Image image = new Image("file:" + imagePath);

        TrackBean track = new TrackBean();
        track.setName(trackName);
        track.setDescription(description);
        track.setAddress(address);
        track.setImage(image);

        new ManageController().createTrack(track);

        switchToShifts();
    }

    private void switchToShifts() {
        new SlotChoiceCLI().start();
    }

}
