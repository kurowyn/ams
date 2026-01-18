package ui;

import service.AuthService;
import java.util.Scanner;

public class LoginMenu {

    public static void start() {

        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();

        System.out.println("===== AUTHENTIFICATION =====");
        System.out.println("1. Étudiant");
        System.out.println("2. Enseignant");
        System.out.println("3. Responsable");
        System.out.print("Choix : ");
        int role = sc.nextInt();
        sc.nextLine();

        System.out.print("Login : ");
        String login = sc.nextLine();

        System.out.print("Mot de passe : ");
        String pwd = sc.nextLine();

        Integer userId = null;

        switch (role) {
            case 1:
                userId = auth.loginEtudiant(login, pwd);
                break;
            case 2:
                userId = auth.loginEnseignant(login, pwd);
                break;
            case 3:
                userId = auth.loginResponsable(login, pwd);
                break;
            default:
                System.out.println("❌ Choix invalide");
                return;
        }

        if (userId == null) {
            System.out.println("❌ Login ou mot de passe incorrect");
            return;
        }

        System.out.println("✅ Connexion réussie");

        if (role == 1) MenuEtudiant.afficherMenu(userId);
        if (role == 2) MenuEnseignant.afficherMenu(userId);
        if (role == 3) MenuResponsable.afficherMenu(userId);
    }
}
