package views;

import beans.ShiftsBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import utils.Session;

public class ShiftsController {

    @FXML
    private TextField opening;
    @FXML
    private TextField closing;
    @FXML
    private TextField duration;
    @FXML
    private TextField karts;
    @FXML
    private Text ownerName;
    @FXML
    private Text success;
    @FXML
    public void initialize() {
        success.setVisible(false);
        ownerName.setText(Session.getInstance().getLoggedUser().getUsername() + " ci siamo quasi...");
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void logout(){
        Session.getInstance().setLoggedUser(null);
        SceneManager.changeScene("/main.fxml");
        SceneManager.showScene();
    }

    @FXML
    public void saveTrack(){
        int opHour = Integer.parseInt(opening.getText());
        int clHour = Integer.parseInt(closing.getText());
        int shiftsDuration = Integer.parseInt(duration.getText());
        int avKarts = Integer.parseInt(karts.getText());
        ShiftsBean shifts = new ShiftsBean(avKarts, opHour, clHour, shiftsDuration);
        ManageController.getInstance().saveShifts(shifts);
        SceneManager.changeScene("/ges.fxml");
    }

}
