package com.miniproject.model;

public class Classe {
    private int id;
    private String libelle;
    private String niveau;
    private String filiere;

    public Classe(int id, String libelle, String niveau, String filiere) {
        this.id = id;
        this.libelle = libelle;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getFiliere() {
        return filiere;
    }

    @Override
    public String toString() {
        return libelle + " (" + filiere + ")";
    }
}
