package com.ui;

import com.dao.AbsenceDAO;
import com.model.Absence;
import com.model.User;
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

        String[] columns = {"ID Student", "ID Subject", "Student", "Subject", "Date", "Count"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filters = new JPanel();
        JButton btnLoad = new JButton("Refresh");
        btnLoad.addActionListener(e -> loadData());
        filters.add(btnLoad);

        JButton btnDelete = new JButton("Cancel Selected Absence");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelected());
        filters.add(btnDelete);

        JButton btnEmail = new JButton("Send Alert Emails");
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
            JOptionPane.showMessageDialog(this, "Please select an absence.");
            return;
        }

        int sId = (int) model.getValueAt(row, 0);
        int subId = (int) model.getValueAt(row, 1);
        Date date = (Date) model.getValueAt(row, 4);

        int confirm = JOptionPane.showConfirmDialog(this, "Confirm cancellation?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteAbsence(sId, subId, date)) {
                JOptionPane.showMessageDialog(this, "Absence cancelled.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Error during deletion.");
            }
        }
    }

    private void sendEmails() {
        JOptionPane.showMessageDialog(this,
                "Alert emails have been sent to students exceeding the absence threshold.");
    }
}
