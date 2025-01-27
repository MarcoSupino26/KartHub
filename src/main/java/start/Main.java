package start;

import exceptions.DataLoadException;
import javafx.application.Application;
import javafx.stage.Stage;
import views.SceneManager;
import java.util.Scanner;
import views.cli.HomeCLI;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    private static boolean isCLI = false;
    private static boolean isDemoMode = true;

    public static void main(String[] args) {
        try {
            loadConfig();
        }
        catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }

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

    //Caricamento del file di config
    private static void loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream configFile = new FileInputStream("src/main/resources/config.properties");
            properties.load(configFile);

            String mode = properties.getProperty("mode", "demo"); // Default a demo se non trovato
            isDemoMode = "demo".equalsIgnoreCase(mode);

            System.out.println("Modalità di esecuzione: " + (isDemoMode ? "Demo" : "Persistence"));

        } catch (IOException e) {
            throw new DataLoadException("File opening error");
        }
    }

    public static boolean isDemoMode() {
        return isDemoMode;
    }

    public static boolean isCLI() {
        return isCLI;
    }
}
