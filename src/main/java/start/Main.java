package start;

import exceptions.DataLoadException;
import javafx.application.Application;
import javafx.stage.Stage;
import graphicalcontroller.gui.SceneManager;
import graphicalcontroller.cli.HomeCLI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Application {

    private static boolean isCLI = false;
    private static String executionMode = "demo";

    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto in KartHub!");
        System.out.println("Scegli la modalità di esecuzione:");
        System.out.println("1. GUI");
        System.out.println("2. CLI");
        System.out.print("-> ");

        String choice = scanner.nextLine();

        switch (choice) {
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
        SceneManager.changeScene("/views/Home.fxml");
    }

    private static void startCLI() {
        HomeCLI homeCLI = new HomeCLI();
        homeCLI.start();
    }

    // Caricamento del file di configurazione
    private static void loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream configFile = new FileInputStream("src/main/resources/config/config.properties");
            properties.load(configFile);

            executionMode = properties.getProperty("mode", "demo").toLowerCase(); // Default a demo se non trovato
            System.out.println("Modalità di esecuzione: " + executionMode);

        } catch (IOException e) {
            throw new DataLoadException(e.getMessage());
        }
    }

    public static String getExecutionMode() {
        return executionMode;
    }

    public static boolean isCLI() {
        return isCLI;
    }
}
