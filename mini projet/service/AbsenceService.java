package service;

import dao.AbsenceDAO;
import java.util.Date;

public class AbsenceService {

    private AbsenceDAO absenceDAO = new AbsenceDAO();

    public void marquerAbsence(int idEtudiant, int idEnseignant,
                               int idMatiere, int numSeance) {

        absenceDAO.ajouterAbsence(
                idEtudiant,
                idEnseignant,
                idMatiere,
                numSeance,
                new Date()
        );
    }

    public void justifierAbsence(int idEtudiant, int idMatiere, int numSeance) {
        absenceDAO.annulerAbsence(idEtudiant, idMatiere, numSeance);
    }
}
