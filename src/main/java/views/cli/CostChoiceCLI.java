package views.cli;

import controllers.ManageController;
import beans.CostBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CostChoiceCLI {

    private Scanner scanner = new Scanner(System.in);
    OwnerTopBarCLI ownerTopBarCLI = new OwnerTopBarCLI();

    private String raceCost;
    private String qualiCost;
    private String fpCost;
    private String champagneCost;
    private String onBoardCost;
    private String medalsCost;

    public void start() {
        System.out.println("Imposta i costi:");

        System.out.print("Costo gara : ");
        raceCost = scanner.nextLine();

        System.out.print("Costo ualifiche: ");
        qualiCost = scanner.nextLine();

        System.out.print("Costo prova libera: ");
        fpCost = scanner.nextLine();

        System.out.print("Costo champagne: ");
        champagneCost = scanner.nextLine();

        System.out.print("Costo on-board: ");
        onBoardCost = scanner.nextLine();

        System.out.print("Costo medaglie: ");
        medalsCost = scanner.nextLine();

        System.out.println("\nHai inserito i seguenti costi:");
        System.out.println("Costo per la gara: €" + raceCost);
        System.out.println("Costo per le qualifiche: €" + qualiCost);
        System.out.println("Costo per la prova libera: €" + fpCost);
        System.out.println("Costo per lo champagne: €" + champagneCost);
        System.out.println("Costo per l'on-board: €" + onBoardCost);
        System.out.println("Costo per le medaglie: €" + medalsCost);

        System.out.print("\nVuoi salvare questi costi? (s/n): ");
        String confirmation = scanner.nextLine();

        if ("s".equalsIgnoreCase(confirmation)) {
            saveCosts();
        } else {
            System.out.println("Operazione annullata.");
        }
        ownerTopBarCLI.switchToHome();
    }

    private void saveCosts() {
        List<Double> costs = new ArrayList<>();
        costs.add(Double.parseDouble(raceCost));
        costs.add(Double.parseDouble(qualiCost));
        costs.add(Double.parseDouble(fpCost));
        costs.add(Double.parseDouble(champagneCost));
        costs.add(Double.parseDouble(medalsCost));
        costs.add(Double.parseDouble(onBoardCost));

        CostBean costBean = new CostBean(costs);

        new ManageController().saveTrack(costBean);
    }

}
