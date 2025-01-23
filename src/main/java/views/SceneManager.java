package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage initialStage;

    public SceneManager(){}

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
            e.printStackTrace();
            System.err.println("Caricamento di" + fxmlPath + "fallito");
        }
    }

    /*public static <T> T getController(String fxmlPath){
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Caricamento di" + fxmlPath + "fallito");
        }
    }*/
}