package start;

import javafx.application.Application;
import javafx.stage.Stage;
import views.SceneManager;
import java.util.Scanner;
import views.cli.HomeCLI;

public class Main extends Application {

    private static boolean isCLI = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto in KartHub!");
        System.out.println("Scegli la modalità di esecuzione:");
        System.out.println("1. GUI");
        System.out.println("2. CLI");
        System.out.print("-> ");

        String scelta = scanner.nextLine();

        switch (scelta) {
            case "1":
                System.out.println("Avvio modalità GUI...");
                Application.launch(Main.class, args);
                break;

            case "2":
                System.out.println("Avvio modalità CLI...");
                isCLI = true;
                startCLI();
                break;

            default:
                System.out.println("Scelta non valida, il programma si chiuderà.");
                System.exit(0);
        }
    }

    @Override
    public void start(Stage initialStage) {
        if (isCLI) {
            return;
        }
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/home.fxml");
    }

    private static void startCLI() {
        HomeCLI homeCLI = new HomeCLI();
        homeCLI.start();
    }
}


