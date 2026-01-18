package com.ui;

import com.dao.AbsenceDAO;
import com.dao.CommonDAO;
import com.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddAbsencePanel extends JPanel {
    private CommonDAO commonDAO;
    private AbsenceDAO absenceDAO;
    private User currentUser;

    private JComboBox<SchoolClass> classComboBox;
    private JComboBox<Subject> subjectComboBox;
    private JPanel studentsPanel;
    private List<JCheckBox> studentCheckBoxes;

    public AddAbsencePanel(User user) {
        this.currentUser = user;
        this.commonDAO = new CommonDAO();
        this.absenceDAO = new AbsenceDAO();
        this.studentCheckBoxes = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Control Panel
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Selection"));

        controlPanel.add(new JLabel("Class:"));
        classComboBox = new JComboBox<>();
        classComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadStudents();
            }
        });
        controlPanel.add(classComboBox);

        controlPanel.add(new JLabel("Subject:"));
        subjectComboBox = new JComboBox<>();
        controlPanel.add(subjectComboBox);

        add(controlPanel, BorderLayout.NORTH);

        // Center Students List
        studentsPanel = new JPanel();
        studentsPanel.setLayout(new BoxLayout(studentsPanel, BoxLayout.Y_AXIS));
        studentsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(studentsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Mark Absent Students"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save Absences");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(220, 53, 69)); // Red color
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveAbsences());
        btnPanel.add(btnSave);

        add(btnPanel, BorderLayout.SOUTH);

        // Initialize Data
        loadClasses();
        loadSubjects();
    }

    private void loadClasses() {
        classComboBox.removeAllItems();
        List<SchoolClass> classes = commonDAO.getAllClasses();
        for (SchoolClass sc : classes) {
            classComboBox.addItem(sc);
        }
        if (classComboBox.getItemCount() > 0) {
            classComboBox.setSelectedIndex(0);
            loadStudents(); // Load students for first class
        }
    }

    private void loadSubjects() {
        subjectComboBox.removeAllItems();
        List<Subject> subjects = commonDAO.getAllSubjects();
        for (Subject sub : subjects) {
            subjectComboBox.addItem(sub);
        }
    }

    private void loadStudents() {
        studentsPanel.removeAll();
        studentCheckBoxes.clear();

        SchoolClass selectedClass = (SchoolClass) classComboBox.getSelectedItem();
        if (selectedClass != null) {
            List<Student> students = commonDAO.getStudentsByClass(selectedClass.getId());

            if (students.isEmpty()) {
                studentsPanel.add(new JLabel("No students found in this class."));
            } else {
                for (Student s : students) {
                    JCheckBox cb = new JCheckBox(
                            s.getFirstName() + " " + s.getLastName() + " (" + s.getUserName() + ")");
                    cb.putClientProperty("student", s); // Store student object in checkbox
                    cb.setBackground(Color.WHITE);
                    studentCheckBoxes.add(cb);
                    studentsPanel.add(cb);
                }
            }
        }
        studentsPanel.revalidate();
        studentsPanel.repaint();
    }

    private void saveAbsences() {
        SchoolClass selectedClass = (SchoolClass) classComboBox.getSelectedItem();
        Subject selectedSubject = (Subject) subjectComboBox.getSelectedItem();

        if (selectedClass == null || selectedSubject == null) {
            JOptionPane.showMessageDialog(this, "Please select a class and a subject.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int count = 0;
        int failed = 0;
        Date now = new Date();

        for (JCheckBox cb : studentCheckBoxes) {
            if (cb.isSelected()) {
                Student s = (Student) cb.getClientProperty("student");
                // Create absence record
                // Assuming count=1 for a single session absence
                Absence absence = new Absence(
                        s.getId(),
                        currentUser.getId(), // Teacher ID
                        selectedSubject.getId(),
                        now,
                        1);

                if (absenceDAO.addAbsence(absence)) {
                    count++;
                } else {
                    failed++;
                }
            }
        }

        if (count > 0) {
            String msg = count + " absence(s) recorded successfully.";
            if (failed > 0)
                msg += "\n" + failed + " failed to record.";
            JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);

            for (JCheckBox cb : studentCheckBoxes)
                cb.setSelected(false);
        } else if (failed > 0) {
            JOptionPane.showMessageDialog(this, "Failed to record absences. Check logs.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No students selected.", "Info", JOptionPane.WARNING_MESSAGE);
        }
    }
}
