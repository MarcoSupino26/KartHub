package views.cli;

import controllers.AuthController;
import beans.LoginBean;
import exceptions.UserNotFoundException;

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
                    break;
                case "2":
                    switchToSign();
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
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

        try {
            if (new AuthController().authUser(logBean)) {
                new HomeCLI().start();
            }
        }catch (UserNotFoundException e){
            e.handleException();
        }
    }

    public void switchToSign() {
        new SignUpCLI().start();
    }
}
