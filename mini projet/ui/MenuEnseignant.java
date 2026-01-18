package ui;

import service.AbsenceService;
import service.ConsultationService;
import java.util.Scanner;

public class MenuEnseignant {

    public static void afficherMenu(int idEnseignant) {
        Scanner sc = new Scanner(System.in);
        AbsenceService absenceService = new AbsenceService();
        ConsultationService consultationService = new ConsultationService();

        System.out.println("===== MENU ENSEIGNANT =====");
        System.out.println("1. Ajouter des absences");
        System.out.println("2. Consulter absences d’une classe");
        System.out.println("3. Quitter");
        System.out.print("Choix : ");

        int choix = sc.nextInt();

        switch (choix) {
            case 1 : {
                System.out.print("ID Étudiant : ");
                int etu = sc.nextInt();

                System.out.print("ID Matière : ");
                int mat = sc.nextInt();

                System.out.print("Numéro séance : ");
                int seance = sc.nextInt();

                absenceService.marquerAbsence(etu, idEnseignant, mat, seance);
            }
            case 2 : {
                System.out.print("ID Classe : ");
                int classe = sc.nextInt();
                consultationService.getAbsencesClasse(classe);
            }
            case 3 : System.out.println("Déconnexion enseignant.");
            default : System.out.println(" Choix invalide");
        }
    }
}
