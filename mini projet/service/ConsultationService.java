package service;

import util.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel; // NEW: For GUI tables

public class ConsultationService {

    // MODIFIED: Instead of printing to console, returns a Table Model for the GUI
    public DefaultTableModel getAbsencesEtudiant(int idEtudiant) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Matière");
        model.addColumn("Séance");
        model.addColumn("Date");

        String sql = "SELECT id_matiere, numseance, date FROM Absence WHERE id_etudiant=?";
        
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getDate(3)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return model;
    }

    // NEW: Logic to fetch all absences for a specific class (for Teacher/Responsible)
    public DefaultTableModel getAbsencesClasse(int idClasse) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Étudiant");
        model.addColumn("Matière");
        model.addColumn("Séance");
        
        String sql = "SELECT A.id_etudiant, A.id_matiere, A.numseance FROM Absence A " +
                     "JOIN Etudiant E ON A.id_etudiant = E.id_etudiant WHERE E.id_classe=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idClasse);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getInt(3)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return model;
    }
}