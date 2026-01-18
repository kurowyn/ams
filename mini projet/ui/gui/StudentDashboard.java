package ui.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import service.ConsultationService; // NEW: Interaction with the service layer

public class StudentDashboard extends JFrame {
    private int studentId;
    private JTable tableAbsences;
    private ConsultationService consultationService = new ConsultationService();

    public StudentDashboard(int id) {
        this.studentId = id;
        
        // Window Settings
        setTitle("Espace Étudiant - Mes Absences");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel lblHeader = new JLabel("Liste de vos absences (ID: " + id + ")", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblHeader, BorderLayout.NORTH);

        // Center: The Table
        // NEW: We call the modified service method that returns a DefaultTableModel
        DefaultTableModel model = consultationService.getAbsencesEtudiant(studentId);
        tableAbsences = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableAbsences);
        add(scrollPane, BorderLayout.CENTER);

        // Footer: Logout button
        JButton btnLogout = new JButton("Déconnexion");
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnLogout);
        add(panelBottom, BorderLayout.SOUTH);

        // Action
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}