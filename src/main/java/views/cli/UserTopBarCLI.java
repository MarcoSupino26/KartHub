package views.cli;

import utils.SessionManager;
import views.SceneManager;
import java.util.Scanner;

public class UserTopBarCLI {
    public void displayMenu() {
        System.out.println("1. Home");
        System.out.println("2. Prenota");
        System.out.println("3. Eventi");
        System.out.println("4. Logout");
    }

    public void switchToHome() {
        SessionManager sessionManager = SessionManager.getInstance();
        if (sessionManager.getLoggedUser() != null) {
            if (sessionManager.getBookingSession() != null) sessionManager.freeBookingSession();
            if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
        }
        new HomeCLI().start();
    }

    public void switchToPrenota() {
        SessionManager sessionManager = SessionManager.getInstance();
        if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
        if (sessionManager.getLoggedUser() != null) {
            new BookingCLI().start();
        } else {
            switchToLogin();
        }
    }

    public void switchToEventi() {
        if (SessionManager.getInstance().getLoggedUser() != null) {
            SceneManager.changeScene("/eventi.fxml");
        } else {
            SceneManager.changeScene("/login.fxml");
        }
    }

    public void switchToLogin() {
        if (SessionManager.getInstance().getLoggedUser() == null) {
            new LoginCLI().start();
        } else {
            SessionManager sessionManager = SessionManager.getInstance();
            if (sessionManager.getBookingSession() != null) sessionManager.freeBookingSession();
            if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
            sessionManager.freeSession();
            new HomeCLI().start();
        }
    }
}
