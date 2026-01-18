package com.miniproject.ui;

import com.miniproject.dao.AbsenceDAO;
import com.miniproject.model.Absence;
import com.miniproject.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AbsenceListPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private User currentUser;
    private AbsenceDAO dao;

    public AbsenceListPanel(User user) {
        this.currentUser = user;
        this.dao = new AbsenceDAO();
        setLayout(new BorderLayout());

        // Updated columns to match new schema (No Seance, added Count)
        String[] columns = { "Student", "Subject", "Date", "Count" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());

        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(e -> {
            try {
                table.print();
            } catch (java.awt.print.PrinterException ex) {
                ex.printStackTrace();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(btnRefresh);
        topPanel.add(btnPrint);
        add(topPanel, BorderLayout.NORTH);

        loadData();
    }

    private void loadData() {
        System.out.println("[AbsenceListPanel] Loading absence data for user: " + currentUser.getUserName() + " (Role: "
                + currentUser.getRole() + ")");

        model.setRowCount(0);
        List<Absence> list = dao.getAllAbsences();

        System.out.println("[AbsenceListPanel] Retrieved " + list.size() + " total absences from database");

        int displayedCount = 0;
        for (Absence a : list) {
            // Filter for student
            if ("STUDENT".equals(currentUser.getRole()) && a.getIdStudent() != currentUser.getId()) {
                continue;
            }

            model.addRow(new Object[] {
                    a.getStudentName(),
                    a.getSubjectName(),
                    a.getDate(),
                    a.getCount()
            });
            displayedCount++;
        }

        System.out.println("[AbsenceListPanel] Displaying " + displayedCount + " absences after filtering");

        // Show informative message if no data
        if (displayedCount == 0 && list.size() == 0) {
            System.out.println("[AbsenceListPanel] No absences in database - showing info message");
            // Only show message on manual refresh, not on initial load
            if (model.getRowCount() == 0) {
                String message;
                if ("STUDENT".equals(currentUser.getRole())) {
                    message = "No absences recorded for your account yet.";
                } else if ("TEACHER".equals(currentUser.getRole())) {
                    message = "No absences in the system yet.\n\nYou can record absences using the 'Record Absence' tab.";
                } else {
                    message = "No absences in the system yet.\n\nTeachers can record absences using the 'Record Absence' tab.";
                }
                // Don't show on initial load to avoid annoying popup
                // JOptionPane.showMessageDialog(this, message, "Info",
                // JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (displayedCount == 0 && list.size() > 0) {
            System.out.println("[AbsenceListPanel] Student has no absences (filtered out " + list.size() + " records)");
        }
    }
}
