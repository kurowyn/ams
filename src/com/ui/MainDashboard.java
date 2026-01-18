package com.ui;

import com.model.User;
import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;

    public MainDashboard(User user) {
        this.currentUser = user;
        setTitle("Absence Management - Dashboard [" + user.getFirstName() + " " + user.getLastName() + "]");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Everyone can see absences
        tabbedPane.addTab("View Absences", new AbsenceListPanel(user));

        // Using internal constant roles from User classes ("TEACHER", "ADMIN",
        // "STUDENT")
        if ("TEACHER".equals(user.getRole())) {
            tabbedPane.addTab("Record Absence", new AddAbsencePanel(user));
        }

        if ("ADMIN".equals(user.getRole())) {
            tabbedPane.addTab("Administration", new AdminPanel(user));
            tabbedPane.addTab("Statistics", new StatsPanel());
            tabbedPane.addTab("Send Email", new SendEmailPanel(user));
            tabbedPane.addTab("View Sent Emails", new ViewEmailsPanel(user));
        }

        if ("STUDENT".equals(user.getRole())) {
            tabbedPane.addTab("My Emails", new StudentEmailPanel(user));
        }

        add(tabbedPane);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        bottomPanel.add(btnLogout);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
