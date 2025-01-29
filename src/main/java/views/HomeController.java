package views;

import exceptions.DataLoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import utils.session.SessionManager;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() {
        try{
            loadTopBar();
        }catch(DataLoadException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadTopBar() {
        FXMLLoader loader;

        //A seconda dell'utente viene caricata la topBar corrispondente
        String fxmlPath = (SessionManager.getInstance().getLoggedUser() != null &&
                "Proprietario".equals(SessionManager.getInstance().getLoggedUser().getType()))
                ? "/OwnerTopBar.fxml"
                : "/UserTopBar.fxml";

        loader = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            BorderPane topBar = loader.load();
            borderPane.setTop(topBar);
        }catch (IOException e){
            throw new DataLoadException("Top bar loading error");
        }
    }
}