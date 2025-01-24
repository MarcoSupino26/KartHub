package start;

import javafx.application.Application;
import javafx.stage.Stage;
import views.SceneManager;
import java.util.Scanner;
import views.cli.HomeCLI;

/*public class Main extends Application {

    @Override
    public void start(Stage initialStage){
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/home.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}*/
public class Main extends Application {

    @Override
    public void start(Stage initialStage) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto in KartHub!");
        System.out.println("Scegli la modalità di esecuzione:");
        System.out.println("1. GUI");
        System.out.println("2. CLI");

        String scelta = scanner.nextLine();

        switch (scelta) {
            case "1":
                launch();
                break;

            case "2":
                System.out.println("Avvio modalità CLI...");
                startCLI();
                break;

            default:
                System.out.println("Scelta non valida, il programma si chiuderà.");
                break;
        }
    }

    private static void startCLI(){
        HomeCLI homeCLI = new HomeCLI();
        homeCLI.start();
    }
}

