package views.cli;

import utils.session.SessionManager;

public class UserTopBarCLI {
    public void displayMenu() {
        System.out.println("-------------------------");
        String log;
        System.out.println("1. Home");
        System.out.println("2. Prenota");
        System.out.println("3. Eventi");
        if(SessionManager.getInstance().getLoggedUser() != null) log = "Logout";
        else log = "Log in";
        System.out.println("4. " + log);
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
        if(SessionManager.getInstance().getLoggedUser()!=null){
            if(sessionManager.getEventSession()!=null) sessionManager.freeEventSession();
            new TrackChoiceCLI().start();
        }
        else new LoginCLI().start();
    }

    public void switchToEventi() {
        if (SessionManager.getInstance().getLoggedUser() != null) {
            new UserEventsCLI().start();
        } else {
            System.out.println("Devi prima registrarti per comprare un biglietto");
            switchToLogin();
        }
    }

    public void switchToLogin() {
        if (SessionManager.getInstance().getLoggedUser() == null) {
            new LoginCLI().start();
        } else {//logout
            SessionManager sessionManager = SessionManager.getInstance();
            if (sessionManager.getBookingSession() != null) sessionManager.freeBookingSession();
            if (sessionManager.getEventSession() != null) sessionManager.freeEventSession();
            sessionManager.freeSession();
            new HomeCLI().start();
        }
    }
}
