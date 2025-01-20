package views;

import beans.CostBean;
import controllers.ManageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import utils.ManageSession;
import utils.SessionManager;
import controllers.ManageController;
import java.util.ArrayList;
import java.util.List;

public class CostController {
    @FXML
    private TextField race;
    @FXML
    private TextField quali;
    @FXML
    private TextField fp;
    @FXML
    private TextField champagne;
    @FXML
    private TextField onBoard;
    @FXML
    private TextField medals;

    @FXML
    public void saveCost(){
        List<Double> cost = new ArrayList<>();
        cost.add(Double.parseDouble(race.getText()));
        cost.add(Double.parseDouble(quali.getText()));
        cost.add(Double.parseDouble(fp.getText()));
        cost.add(Double.parseDouble(champagne.getText()));
        cost.add(Double.parseDouble(medals.getText()));
        cost.add(Double.parseDouble(onBoard.getText()));
        CostBean costBean = new CostBean(cost);
        ManageController.getInstance().saveTrack(costBean);
        SceneManager.changeScene("/main.fxml");
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
}
