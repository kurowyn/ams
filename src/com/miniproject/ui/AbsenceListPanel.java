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
        model.setRowCount(0);
        List<Absence> list = dao.getAllAbsences();

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
        }
    }
}
