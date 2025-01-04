package views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import utils.Session;

public class PrenotaController {

    @FXML
    private CheckBox check;
    @FXML
    private Group optional;

    @FXML
    public void initialize() {
        optional.setVisible(false);
    }

    @FXML
    public void switchToEventi(ActionEvent event){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void switchToHome(Event event){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void logout(Event event){
        Session.getInstance().freeSession();
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void showOptions(Event event){
        boolean checked = check.isSelected();
        optional.setVisible(checked);
        optional.setManaged(checked);
    }
}
