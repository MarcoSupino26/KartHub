package exceptions;

import javafx.scene.control.Alert;

public class PopUpManager {

    public static void showPopUp(String message){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
