package ui.gui;

import javax.swing.*;
import java.awt.*;
import service.AbsenceService;

public class TeacherDashboard extends JFrame {
    private int teacherId;
    private JTextField tEtu, tMat, tSea;
    private AbsenceService service = new AbsenceService();

    public TeacherDashboard(int id) {
        this.teacherId = id;
        setTitle("Espace Enseignant - ID: " + id);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID Étudiant:")); tEtu = new JTextField(); add(tEtu);
        add(new JLabel("ID Matière:")); tMat = new JTextField(); add(tMat);
        add(new JLabel("Séance N°:")); tSea = new JTextField(); add(tSea);

        JButton btnAdd = new JButton("Marquer Absence");
        JButton btnOut = new JButton("Déconnexion");

        add(btnOut); add(btnAdd);

        btnAdd.addActionListener(e -> {
            service.marquerAbsence(Integer.parseInt(tEtu.getText()), teacherId, 
                                   Integer.parseInt(tMat.getText()), Integer.parseInt(tSea.getText()));
            JOptionPane.showMessageDialog(this, "Absence enregistrée !");
        });
        
        btnOut.addActionListener(e -> { new LoginFrame().setVisible(true); this.dispose(); });
    }
}