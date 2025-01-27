package views;

import exceptions.DataLoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage initialStage;

    private SceneManager(){//la classe non deve essere istanziata
    }

    //Inizializzazione riferimento allo stage principale
    public static void setInitialStage(Stage stage) {
        initialStage = stage;
    }

    //Cambio schermata
    public static void changeScene(String fxmlPath){
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load(),850,580);
            initialStage.setScene(scene);
            initialStage.setTitle("KartHub");
            initialStage.setResizable(false);
            initialStage.show();
        } catch (IOException e) {
            throw new DataLoadException("Couldn't load scene " + fxmlPath);
        }
    }
}