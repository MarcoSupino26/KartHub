package start;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage initialStage){
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/main.fxml");
        SceneManager.showScene();
    }

    public static void main(String[] args) {
        launch();
    }
}