package views.topbar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.SessionManager;
import views.SceneManager;

public class UserTopBarController {
    @FXML
    private Button log;

    @FXML
    public void initialize(){
        if(SessionManager.getInstance().getLoggedUser()!=null){
            log.setText("Logout");
        }
    }

    @FXML
    public void switchToHome(){
        SceneManager.changeScene("/main.fxml");
    }

    @FXML
    public void switchToPrenota(){
        if(SessionManager.getInstance().getLoggedUser()!=null){SceneManager.changeScene("/book.fxml");}
        else switchToLogin();
    }

    @FXML
    public void switchToEventi(){
        if(SessionManager.getInstance().getLoggedUser()!=null) SceneManager.changeScene("/eventi.fxml");
        else SceneManager.changeScene("/login.fxml");
    }

    @FXML
    public void switchToLogin(){
        if(SessionManager.getInstance().getLoggedUser() == null){
            SceneManager.changeScene("/login.fxml");
        }else {
            SessionManager.getInstance().freeSession();
            SceneManager.changeScene("/main.fxml");
        }
    }
}

