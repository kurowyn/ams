package model;

import java.util.Date;

public class Absence {
    private int idEtudiant;
    private int idEnseignant;
    private int idMatiere;
    private int numSeance;
    private Date date;

    public Absence(int idEtudiant, int idEnseignant, int idMatiere, int numSeance, Date date) {
        this.idEtudiant = idEtudiant;
        this.idEnseignant = idEnseignant;
        this.idMatiere = idMatiere;
        this.numSeance = numSeance;
        this.date = date;
    }
}
