package model;

public class Enseignant extends Utilisateur {

    public Enseignant(int id, String nom, String prenom, String login, String pwd) {
        super(id, nom, prenom, login, pwd);
    }

    @Override
    public String getRole() {
        return "ENSEIGNANT";
    }
}
