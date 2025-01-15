package views;

import beans.ShiftsBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import utils.SessionManager;

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
        ownerName.setText(SessionManager.getInstance().getLoggedUser().getUsername() + " ci siamo quasi...");
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToEventi(){
        SceneManager.changeScene("/eventi.fxml");
    }

    @FXML
    public void logout(){
        SessionManager.getInstance().setLoggedUser(null);
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void saveTrack(){
        double opHour = Double.parseDouble(opening.getText());
        double clHour = Double.parseDouble(closing.getText());
        int shiftsDuration = Integer.parseInt(duration.getText());
        int avKarts = Integer.parseInt(karts.getText());
        ShiftsBean shifts = new ShiftsBean(avKarts, opHour, clHour, shiftsDuration);
        ManageController.getInstance().saveShifts(shifts);
        SceneManager.changeScene("/trackmanager.fxml");
    }

}
