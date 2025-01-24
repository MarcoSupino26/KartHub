package views.cli;

import controllers.AuthController;
import beans.LoginBean;
import java.util.Scanner;

public class SignUpCLI {

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("-------------------------");
        System.out.print("Inserisci il tuo nome utente: ");
        String username = scanner.nextLine();

        System.out.print("Inserisci la tua password: ");
        String password = scanner.nextLine();

        System.out.println("Seleziona il tipo di utente:");
        System.out.println("1. Proprietario");
        System.out.println("2. Utente");
        String userTypeChoice = scanner.nextLine();
        String userType = userTypeChoice.equals("1") ? "Proprietario" : "Utente";

        LoginBean signBean = new LoginBean();
        signBean.setUsername(username);
        signBean.setPassword(password);
        signBean.setType(userType);

        new AuthController().registerUser(signBean);
        System.out.println("Registrazione riuscita! Benvenuto, " + username);
        new HomeCLI().start();
    }
}

