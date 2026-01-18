package ui;

import service.ConsultationService;
import java.util.Scanner;

public class MenuEtudiant {

    public static void afficherMenu(int idEtudiant) {
        Scanner sc = new Scanner(System.in);
        ConsultationService service = new ConsultationService();

        System.out.println("===== MENU ÉTUDIANT =====");
        System.out.println("1. Consulter mes absences");
        System.out.println("2. Quitter");
        System.out.print("Choix : ");

        int choix = sc.nextInt();

        if (choix == 1) {
            service.getAbsencesEtudiant(idEtudiant);
        }

        System.out.println("Déconnexion étudiant.");
    }
}
