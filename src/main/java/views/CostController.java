package views;

import beans.CostBean;
import controllers.ManageController;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
        new ManageController().saveTrack(costBean);
        try {
            SceneManager.changeScene("/Home.fxml");
        } catch (DataLoadException e){
            System.out.println(e.getMessage());
        }
    }
}
