package com.miniproject.ui;

import com.miniproject.dao.UserDAO;
import com.miniproject.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Absence Management - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Welcome");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        String[] roles = { "STUDENT", "TEACHER", "ADMIN" };
        cbRole = new JComboBox<>(roles);
        panel.add(cbRole, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        panel.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(70, 130, 180)); // Steel Blue
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.addActionListener(this::handleLogin);
        panel.add(btnLogin, gbc);

        add(panel);
    }

    private void handleLogin(ActionEvent e) {
        String login = txtLogin.getText();
        String pwd = new String(txtPassword.getPassword());
        String role = (String) cbRole.getSelectedItem();

        UserDAO dao = new UserDAO();
        User user = dao.authenticate(login, pwd, role);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getFirstName() + " (" + user.getRole() + ")");
            new MainDashboard(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password for role " + role, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
