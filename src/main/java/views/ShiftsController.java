package views;

import beans.ShiftsBean;
import controllers.ManageController;
import exceptions.DataLoadException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import utils.session.SessionManager;

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
    public void initialize() {
        ownerName.setText(SessionManager.getInstance().getLoggedUser().getUsername() + ", imposta gli orari");
    }

    @FXML
    public void switchToCost(){
        //Invio dei dati dei turni lavorativi
        double opHour = Double.parseDouble(opening.getText());
        double clHour = Double.parseDouble(closing.getText());
        int shiftsDuration = Integer.parseInt(duration.getText());
        int avKarts = Integer.parseInt(karts.getText());
        ShiftsBean shifts = new ShiftsBean(avKarts, opHour, clHour, shiftsDuration);
        new ManageController().saveShifts(shifts);
        try{
            SceneManager.changeScene("/costChoice.fxml");
        }catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }

}
