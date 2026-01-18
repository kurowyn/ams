package ui.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtId;
    private JComboBox<String> comboRole;

    public LoginFrame() {
        setTitle("Connexion - Gestion Absences");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 5, 5));

        add(new JLabel("Role :", SwingConstants.CENTER));
        comboRole = new JComboBox<>(new String[]{"Étudiant", "Enseignant", "Responsable"});
        add(comboRole);

        add(new JLabel("Identifiant (ID) :", SwingConstants.CENTER));
        txtId = new JTextField();
        add(txtId);

        JButton btnLogin = new JButton("Se Connecter");
        add(btnLogin);

        btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String role = (String) comboRole.getSelectedItem();

            if (role.equals("Enseignant")) new TeacherDashboard(id).setVisible(true);
            else if (role.equals("Responsable")) new ResponsableDashboard(id).setVisible(true);
            else new StudentDashboard(id).setVisible(true);

            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un ID valide.");
        }
    }
}