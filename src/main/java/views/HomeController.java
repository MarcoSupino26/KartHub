package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import utils.SessionManager;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize(){
        loadTopBar();
    }

    private void loadTopBar() {
        FXMLLoader loader;

        String fxmlPath = (SessionManager.getInstance().getLoggedUser() != null &&
                "Proprietario".equals(SessionManager.getInstance().getLoggedUser().getType()))
                ? "/ownertopbar.fxml"
                : "/usertopbar.fxml";

        loader = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            BorderPane topBar = loader.load();
            borderPane.setTop(topBar);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}