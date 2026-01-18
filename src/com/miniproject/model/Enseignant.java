package com.miniproject.model;

public class Enseignant extends User {
    public Enseignant(int id, String nom, String prenom, String login, String pwd) {
        super(id, nom, prenom, login, pwd, "ENSEIGNANT");
    }
}
