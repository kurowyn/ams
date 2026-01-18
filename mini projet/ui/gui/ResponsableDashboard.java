package ui.gui;

import javax.swing.*;
import java.awt.*;
import service.AbsenceService;
import service.ConsultationService;

public class ResponsableDashboard extends JFrame {
    private AbsenceService absenceService = new AbsenceService();
    private ConsultationService consultationService = new ConsultationService();

    public ResponsableDashboard(int respId) {
        setTitle("Espace Responsable - Administration");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 15, 15));

        // Components
        JLabel title = new JLabel("Tableau de Bord Responsable", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JButton btnJustify = new JButton("Justifier / Supprimer une absence");
        JButton btnViewAll = new JButton("Consulter Absences par Classe");
        JButton btnStats = new JButton("Générer Statistiques (Graphiques)");
        JButton btnLogout = new JButton("Déconnexion");

        // Styling (Optional)
        btnJustify.setBackground(new Color(230, 230, 250));
        
        add(btnJustify);
        add(btnViewAll);
        add(btnStats);
        add(btnLogout);

        // ACTIONS
        
        // 1. Justify Action (Uses a Popup Input)
        btnJustify.addActionListener(e -> handleJustification());

        // 2. View All Action (Shows a table in a new popup)
        btnViewAll.addActionListener(e -> {
            String idClasse = JOptionPane.showInputDialog(this, "Entrez l'ID de la classe :");
            if (idClasse != null) {
                showClassAbsences(Integer.parseInt(idClasse));
            }
        });

        // 3. Stats Action (Simple Message for now)
        btnStats.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Calcul des statistiques en cours...\nTaux d'absence global : 12%", 
            "Statistiques", JOptionPane.INFORMATION_MESSAGE));

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void handleJustification() {
        JTextField fEtu = new JTextField();
        JTextField fMat = new JTextField();
        JTextField fSea = new JTextField();
        Object[] inputFields = { "ID Étudiant:", fEtu, "ID Matière:", fMat, "N° Séance:", fSea };

        int result = JOptionPane.showConfirmDialog(this, inputFields, "Justifier une absence", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // NEW: Calls your existing logic in the service layer
            absenceService.justifierAbsence(
                Integer.parseInt(fEtu.getText()), 
                Integer.parseInt(fMat.getText()), 
                Integer.parseInt(fSea.getText())
            );
            JOptionPane.showMessageDialog(this, "L'absence a été retirée avec succès.");
        }
    }

    private void showClassAbsences(int classId) {
        // Create a temporary window to show the table
        JFrame frame = new JFrame("Absences Classe " + classId);
        JTable table = new JTable(consultationService.getAbsencesClasse(classId));
        frame.add(new JScrollPane(table));
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}