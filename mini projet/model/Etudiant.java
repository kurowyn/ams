package model;

public class Etudiant extends Utilisateur {

    private int idClasse;

    public Etudiant(int id, String nom, String prenom, String login, String pwd, int idClasse) {
        super(id, nom, prenom, login, pwd);
        this.idClasse = idClasse;
    }

    @Override
    public String getRole() {
        return "ETUDIANT";
    }

    public int getIdClasse() {
        return idClasse;
    }
}
