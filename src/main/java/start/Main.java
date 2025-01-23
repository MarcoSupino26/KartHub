package start;

import javafx.application.Application;
import javafx.stage.Stage;
import views.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage initialStage){
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/home.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}