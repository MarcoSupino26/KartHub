package start;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Dao.UserDAO;
import models.User;
import views.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage initialStage){
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/main.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}