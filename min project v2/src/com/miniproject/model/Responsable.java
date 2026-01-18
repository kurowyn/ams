package com.miniproject.model;

public class Responsable extends User {
    public Responsable(int id, String nom, String prenom, String login, String pwd) {
        super(id, nom, prenom, login, pwd, "RESPONSABLE");
    }
}
