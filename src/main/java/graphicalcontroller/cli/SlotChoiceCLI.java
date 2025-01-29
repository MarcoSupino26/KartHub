package graphicalcontroller.cli;

import controllers.ManageController;
import beans.ShiftsBean;
import java.util.Scanner;

public class SlotChoiceCLI {

    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    private String opening;
    private String closing;
    private String duration;
    private String karts;

    public void start() {
        System.out.println("-------------------------");
        System.out.println("Imposta gli orari:");

        System.out.print("Orario di apertura (formato 24 ore): ");
        opening = scanner.nextLine();

        System.out.print("Orario di chiusura (formato 24 ore): ");
        closing = scanner.nextLine();

        System.out.print("Durata dei turni (min): ");
        duration = scanner.nextLine();

        System.out.print("Numero di kart disponibili: ");
        karts = scanner.nextLine();

        System.out.println("\nHai inserito i seguenti dati:");
        System.out.println("Orario di apertura: " + opening);
        System.out.println("Orario di chiusura: " + closing);
        System.out.println("Durata dei turni: " + duration + " minuti");
        System.out.println("Kart disponibili: " + karts);

        System.out.print("\nVuoi salvare questi dati? (s/n): ");
        String confirmation = scanner.nextLine();

        if ("s".equalsIgnoreCase(confirmation)) {
            saveShifts();
        } else {
            System.out.println("Operazione annullata.");
            ownerTopBarCLI.switchToHome();
        }
    }

    private void saveShifts() {
        double opHour = Double.parseDouble(opening);
        double clHour = Double.parseDouble(closing);
        int shiftsDuration = Integer.parseInt(duration);
        int avKarts = Integer.parseInt(karts);

        ShiftsBean shifts = new ShiftsBean(avKarts, opHour, clHour, shiftsDuration);

        new ManageController().saveShifts(shifts);

        new CostChoiceCLI().start();
    }
}

