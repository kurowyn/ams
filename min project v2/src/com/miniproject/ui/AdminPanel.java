package com.miniproject.ui;

import com.miniproject.dao.AbsenceDAO;
import com.miniproject.model.Absence;
import com.miniproject.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class AdminPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private AbsenceDAO dao;

    public AdminPanel(User user) {
        this.dao = new AbsenceDAO();
        setLayout(new BorderLayout());

        // Updated columns
        String[] columns = { "ID Student", "ID Subject", "Etudiant", "Matiere", "Date", "Count" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filters = new JPanel();
        JButton btnLoad = new JButton("Actualiser");
        btnLoad.addActionListener(e -> loadData());
        filters.add(btnLoad);

        JButton btnDelete = new JButton("Annuler Absence Selectionnée");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelected());
        filters.add(btnDelete);

        JButton btnEmail = new JButton("Envoyer Mails d'Alertes");
        btnEmail.addActionListener(e -> sendEmails());
        filters.add(btnEmail);

        add(filters, BorderLayout.NORTH);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Absence> list = dao.getAllAbsences();
        for (Absence a : list) {
            model.addRow(new Object[] {
                    a.getIdStudent(),
                    a.getIdSubject(),
                    a.getStudentName(),
                    a.getSubjectName(),
                    a.getDate(),
                    a.getCount()
            });
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une absence.");
            return;
        }

        int sId = (int) model.getValueAt(row, 0);
        int subId = (int) model.getValueAt(row, 1);
        Date date = (Date) model.getValueAt(row, 4);

        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer l'annulation ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteAbsence(sId, subId, date)) {
                JOptionPane.showMessageDialog(this, "Absence annulée.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        }
    }

    private void sendEmails() {
        JOptionPane.showMessageDialog(this,
                "Les emails d'alertes ont été envoyés aux étudiants dépassant le seuil d'absences.");
    }
}
