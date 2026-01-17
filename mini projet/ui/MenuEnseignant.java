package ui;

import service.AbsenceService;
import java.util.Scanner;

public class MenuEnseignant {

    public static void afficherMenu() {

        Scanner sc = new Scanner(System.in);
        AbsenceService service = new AbsenceService();

        System.out.println("===== MENU ENSEIGNANT =====");
        System.out.println("1. Marquer une absence");
        System.out.println("2. Quitter");
        System.out.print("Choix : ");

        int choix = sc.nextInt();

        if (choix == 1) {

            System.out.print("ID Étudiant : ");
            int etudiant = sc.nextInt();

            System.out.print("ID Matière : ");
            int matiere = sc.nextInt();

            System.out.print("Numéro séance : ");
            int seance = sc.nextInt();

            service.marquerAbsence(etudiant, 1, matiere, seance);
        }
    }
}
