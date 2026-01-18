package ui;

import service.AbsenceService;
import service.ConsultationService;
import service.StatistiqueService;
import service.EmailService;
import java.util.Scanner;

public class MenuResponsable {

    public static void afficherMenu(int idResponsable) {
        Scanner sc = new Scanner(System.in);

        AbsenceService absenceService = new AbsenceService();
        ConsultationService consultationService = new ConsultationService();
        StatistiqueService statistiqueService = new StatistiqueService();
        EmailService emailService = new EmailService();

        System.out.println("===== MENU RESPONSABLE =====");
        System.out.println("1. Consulter absences d’une classe");
        System.out.println("2. Supprimer une absence");
        System.out.println("3. Générer statistiques");
        System.out.println("4. Envoyer mails d’alerte");
        System.out.println("5. Quitter");
        System.out.print("Choix : ");

        int choix = sc.nextInt();

        switch (choix) {
            case 1 : {
                System.out.print("ID Classe : ");
                int idClasse = sc.nextInt();
                consultationService.getAbsencesClasse(idClasse);
            }
            case 2 : {
                System.out.print("ID Étudiant : ");
                int etu = sc.nextInt();
                System.out.print("ID Matière : ");
                int mat = sc.nextInt();
                System.out.print("Séance : ");
                int seance = sc.nextInt();
                absenceService.justifierAbsence(etu, mat, seance);
            }
            case 3 : statistiqueService.genererStatistiquesParClasse();
            case 4 : emailService.envoyerAlertes();
            case 5 : System.out.println("Déconnexion responsable.");
            default : System.out.println(" Choix invalide");
        }
    }
}
