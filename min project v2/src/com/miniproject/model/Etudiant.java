package com.miniproject.model;

public class Etudiant extends User {
    private int idClasse;

    public Etudiant(int id, String nom, String prenom, String login, String pwd, int idClasse) {
        super(id, nom, prenom, login, pwd, "ETUDIANT");
        this.idClasse = idClasse;
    }

    public int getIdClasse() {
        return idClasse;
    }
}
