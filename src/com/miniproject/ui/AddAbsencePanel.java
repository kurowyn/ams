package com.miniproject.ui;

import com.miniproject.dao.AbsenceDAO;
import com.miniproject.dao.CommonDAO;
import com.miniproject.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class AddAbsencePanel extends JPanel {
    private JComboBox<SchoolClass> cbClass;
    private JComboBox<Subject> cbSubject;
    private JTextField txtDate;
    private JTable tableStudents;
    private DefaultTableModel modelStudents;
    private CommonDAO commonDao;
    private User currentUser;

    public AddAbsencePanel(User user) {
        this.currentUser = user;
        this.commonDao = new CommonDAO();
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        cbClass = new JComboBox<>();
        commonDao.getAllClasses().forEach(cbClass::addItem);

        cbSubject = new JComboBox<>();
        commonDao.getAllSubjects().forEach(cbSubject::addItem);

        txtDate = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        topPanel.add(new JLabel("Class:"));
        topPanel.add(cbClass);
        topPanel.add(new JLabel("Subject:"));
        topPanel.add(cbSubject);
        topPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        topPanel.add(txtDate);

        JButton btnLoad = new JButton("Load Students");
        btnLoad.addActionListener(e -> loadStudents());

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.add(topPanel, BorderLayout.CENTER);
        pnlHeader.add(btnLoad, BorderLayout.SOUTH);

        add(pnlHeader, BorderLayout.NORTH);

        // Table with Checkbox
        modelStudents = new DefaultTableModel(new Object[] { "ID", "Last Name", "First Name", "Absent?" }, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        };
        tableStudents = new JTable(modelStudents);
        add(new JScrollPane(tableStudents), BorderLayout.CENTER);

        JButton btnSave = new JButton("Save Absences");
        btnSave.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveAbsences());
        add(btnSave, BorderLayout.SOUTH);
    }

    private void loadStudents() {
        SchoolClass selectedClass = (SchoolClass) cbClass.getSelectedItem();
        if (selectedClass == null)
            return;

        modelStudents.setRowCount(0);
        List<Student> students = commonDao.getStudentsByClass(selectedClass.getId());
        for (Student s : students) {
            modelStudents.addRow(new Object[] { s.getId(), s.getLastName(), s.getFirstName(), false });
        }
    }

    private void saveAbsences() {
        AbsenceDAO absDao = new AbsenceDAO();
        SchoolClass cls = (SchoolClass) cbClass.getSelectedItem();
        Subject subj = (Subject) cbSubject.getSelectedItem();
        String dateStr = txtDate.getText();

        try {
            Date date = java.sql.Date.valueOf(dateStr);
            int count = 0;
            for (int i = 0; i < modelStudents.getRowCount(); i++) {
                Boolean isAbsent = (Boolean) modelStudents.getValueAt(i, 3);
                if (Boolean.TRUE.equals(isAbsent)) {
                    int studentId = (int) modelStudents.getValueAt(i, 0);
                    // Default count 1. Use composite ID logic implicitly or new Model
                    Absence abs = new Absence(studentId, currentUser.getId(), subj.getId(), date, 1);
                    absDao.addAbsence(abs);
                    count++;
                }
            }
            JOptionPane.showMessageDialog(this, count + " absences recorded.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: Check date format (yyyy-MM-dd)");
            ex.printStackTrace();
        }
    }
}
