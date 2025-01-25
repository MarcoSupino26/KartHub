package views.cli;

import controllers.AuthController;
import beans.LoginBean;

import java.util.Scanner;

public class LoginCLI {

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("-------------------------");
            System.out.println("1. Log in");
            System.out.println("2. Registrati");
            System.out.print("Seleziona un'opzione: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    accedi();
                case "2":
                    switchToSign();
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private void accedi() {
        System.out.println("-------------------------");
        System.out.print("Inserisci il tuo nome utente: ");
        String username = scanner.nextLine();

        System.out.print("Inserisci la tua password: ");
        String password = scanner.nextLine();

        LoginBean logBean = new LoginBean();
        logBean.setUsername(username);
        logBean.setPassword(password);

        AuthController authenticator = new AuthController();
        if (authenticator.authUser(logBean)) {
            System.out.println("Accesso riuscito! Benvenuto, " + username);
            new HomeCLI().start();
        } else {
            System.out.println("Credenziali errate. Riprova.");
            start();
        }
    }

    public void switchToSign() {
        new SignUpCLI().start();
    }
}
