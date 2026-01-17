package dao;

import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class AbsenceDAO {

    public void ajouterAbsence(int idEtudiant, int idEnseignant,
                               int idMatiere, int numSeance, Date date) {

        String sql = "INSERT INTO Absence VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idEtudiant);
            ps.setInt(2, idEnseignant);
            ps.setInt(3, idMatiere);
            ps.setInt(4, numSeance);
            ps.setDate(5, new java.sql.Date(date.getTime()));

            ps.executeUpdate();
            System.out.println(" Absence enregistrée avec succès");

        } catch (Exception e) {
            System.out.println(" Erreur lors de l'ajout de l'absence");
        }
    }

    public void annulerAbsence(int idEtudiant, int idMatiere, int numSeance) {

        String sql = "DELETE FROM Absence WHERE id_etudiant=? AND id_matiere=? AND numseance=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idEtudiant);
            ps.setInt(2, idMatiere);
            ps.setInt(3, numSeance);

            ps.executeUpdate();
            System.out.println(" Absence annulée (justifiée)");

        } catch (Exception e) {
            System.out.println(" Erreur lors de l'annulation");
        }
    }
}
