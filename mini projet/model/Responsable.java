package model;

public class Responsable extends Utilisateur {

    public Responsable(int id, String nom, String prenom, String login, String pwd) {
        super(id, nom, prenom, login, pwd);
    }

    @Override
    public String getRole() {
        return "RESPONSABLE";
    }
}
