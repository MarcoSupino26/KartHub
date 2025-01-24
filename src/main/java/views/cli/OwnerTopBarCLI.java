package views.cli;

import controllers.ManageController;
import utils.SessionManager;
import views.SceneManager;

import java.util.Scanner;

public class OwnerTopBarCLI {

    public void displayMenu() {
        System.out.println("1. Home");
        System.out.println("2. Gestisci");
        System.out.println("3. Eventi");
        System.out.println("4. Logout");
    }

    public void switchToHome() {
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        new HomeCLI().start();
    }

    public void switchToManager() {
        if(new ManageController().registeredTrack()) new TrackManagerCLI().start();
        //else new TrackCreationCLI().start;
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
        if(sessionManager.getManageSession()!=null) sessionManager.freeManageSession();
        SessionManager.getInstance().freeSession();
        new HomeCLI().start();
    }

    public void switchToEventi() {
        if(new ManageController().registeredTrack()) new TrackManagerCLI().start();
        //else new TrackCreationCLI().start();
    }
}
