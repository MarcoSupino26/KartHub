package views.cli;

import controllers.BookManager;
import beans.DisplayBean;

import java.util.List;
import java.util.Scanner;

public class TrackChoiceCLI {

    private Scanner scanner = new Scanner(System.in);
    UserTopBarCLI userTopBarCLI = new UserTopBarCLI();

    public void start() {
        userTopBarCLI.displayMenu();
        displayMenu();
        String choice = scanner.nextLine();
        handleChoice(choice);
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
        System.out.println("-> ");
    }

    private void displayTrackProfile(DisplayBean displayBean, int index) {
        System.out.println("\n" + index + ". " + displayBean.getName());
        System.out.println("Descrizione: " + displayBean.getDescription());
        System.out.println("-------------------------");
    }

    private void onSelectClicked(String trackName) {
        BookManager bookManager = new BookManager();
        bookManager.setBookingSession(trackName);
        System.out.println("Hai selezionato il tracciato: " + trackName);
        new BookingCLI().start();
    }

    private void selectTrack(){
        BookManager bookManager = new BookManager();
        List<DisplayBean> profiles = bookManager.getTracks();

        if (profiles.isEmpty()) {
            System.out.println("Nessun tracciato disponibile.");
            return;
        }

        System.out.println("Seleziona un tracciato:");

        for (int i = 0; i < profiles.size(); i++) {
            DisplayBean displayBean = profiles.get(i);
            displayTrackProfile(displayBean, i + 1);
        }

        System.out.print("Inserisci il numero del tracciato che vuoi selezionare: ");
        int selectedTrackIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (selectedTrackIndex >= 0 && selectedTrackIndex < profiles.size()) {
            DisplayBean selectedTrack = profiles.get(selectedTrackIndex);
            onSelectClicked(selectedTrack.getName());
        } else {
            System.out.println("Scelta non valida.");
        }
    }
}
