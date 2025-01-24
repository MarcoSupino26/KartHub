package views.cli;

import controllers.BookManager;
import beans.DisplayBean;
import models.track.Track;
import utils.SessionManager;

import java.util.List;
import java.util.Scanner;

public class TrackChoiceCLI {

    private Scanner scanner = new Scanner(System.in);
    UserTopBarCLI userTopBarCLI = new UserTopBarCLI();

    public void start() {
        while (true) {
            userTopBarCLI.displayMenu();
            displayMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
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
                selectTrack();
                break;
            default:
                System.out.println("Opzione non valida. Riprova");
                break;
        }
    }
    private void displayMenu() {
        System.out.println("5. Seleziona tracciato");
        System.out.print("-> ");
    }

    private void displayTrackProfile(DisplayBean displayBean, int index) {
        System.out.println("\n" + index + ". " + displayBean.getName());
        System.out.println("Descrizione: " + displayBean.getDescription());
        System.out.print("-------------------------");
    }

    private void onSelectClicked(String trackName) {
        BookManager bookManager = new BookManager();
        bookManager.setBookingSession(trackName);
        new BookingCLI().start();
    }

    private void selectTrack(){
        BookManager bookManager = new BookManager();
        List<DisplayBean> profiles = bookManager.getTracks();

        if (profiles.isEmpty()) {
            System.out.println("Nessun tracciato disponibile.");
            return;
        }
        System.out.println("-------------------------");
        for (int i = 0; i < profiles.size(); i++) {
            DisplayBean displayBean = profiles.get(i);
            displayTrackProfile(displayBean, i + 1);
        }

        System.out.print("\nSeleziona tracciato: ");
        int selectedTrackIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (selectedTrackIndex >= 0 && selectedTrackIndex < profiles.size()) {
            DisplayBean selectedTrack = profiles.get(selectedTrackIndex);
            onSelectClicked(selectedTrack.getName());
        } else {
            System.out.println("Scelta non valida.");
        }
    }
}
