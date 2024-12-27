package start;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Dao.UserDAO;
import models.User;
import views.SceneManager;

public class Main extends Application {
    //@Override
    /*public void start(Stage stage){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public void start(Stage initialStage){
        UserDAO memoria = UserDAO.getInstance();
        User utente;
        utente = new User("emanuele", "girobiro03", "Customer");
        memoria.addUser("emanuele", utente);
        utente = new User("Immanuel", "scusi", "Cotton picker");
        memoria.addUser("Immanuel", utente);
        SceneManager.setInitialStage(initialStage);
        SceneManager.changeScene("/main.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

    /*public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 580);
        stage.setTitle("KartHub");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }*/
}