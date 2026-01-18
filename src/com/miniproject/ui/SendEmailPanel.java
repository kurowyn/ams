package com.miniproject.ui;

import com.miniproject.dao.CommonDAO;
import com.miniproject.dao.EmailDAO;
import com.miniproject.model.SchoolClass;
import com.miniproject.model.Student;
import com.miniproject.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SendEmailPanel extends JPanel {
    private EmailDAO emailDAO;
    private CommonDAO commonDAO;
    private User currentUser;

    private JComboBox<SchoolClass> classCombo;
    private JComboBox<Student> studentCombo;
    private JTextField subjectField;
    private JTextArea bodyArea;
    private JButton sendButton;
    private JButton refreshButton;

    public SendEmailPanel(User user) {
        this.currentUser = user;
        this.emailDAO = new EmailDAO();
        this.commonDAO = new CommonDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Send Email to Student", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Class Selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Select Class:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        classCombo = new JComboBox<>();
        classCombo.addActionListener(e -> loadStudents());
        formPanel.add(classCombo, gbc);

        // Student Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Select Student:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        studentCombo = new JComboBox<>();
        formPanel.add(studentCombo, gbc);

        // Subject
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Subject:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        subjectField = new JTextField(30);
        formPanel.add(subjectField, gbc);

        // Message Body
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        bodyArea = new JTextArea(10, 30);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(bodyArea);
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        sendButton = new JButton("Send Email");
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.addActionListener(e -> sendEmail());
        buttonPanel.add(sendButton);

        refreshButton = new JButton("Clear Form");
        refreshButton.addActionListener(e -> clearForm());
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadClasses();
    }

    private void loadClasses() {
        classCombo.removeAllItems();
        List<SchoolClass> classes = commonDAO.getAllClasses();
        for (SchoolClass c : classes) {
            classCombo.addItem(c);
        }
        if (classes.size() > 0) {
            loadStudents();
        }
    }

    private void loadStudents() {
        studentCombo.removeAllItems();
        SchoolClass selectedClass = (SchoolClass) classCombo.getSelectedItem();
        if (selectedClass != null) {
            List<Student> students = commonDAO.getStudentsByClass(selectedClass.getId());
            for (Student s : students) {
                studentCombo.addItem(s);
            }
        }
    }

    private void sendEmail() {
        Student selectedStudent = (Student) studentCombo.getSelectedItem();
        String subject = subjectField.getText().trim();
        String body = bodyArea.getText().trim();

        // Validation
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a subject.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (body.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send email
        boolean success = emailDAO.sendEmail(
                currentUser.getId(),
                selectedStudent.getId(),
                subject,
                body);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Email sent successfully to " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName()
                            + "!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to send email. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        subjectField.setText("");
        bodyArea.setText("");
        if (classCombo.getItemCount() > 0) {
            classCombo.setSelectedIndex(0);
        }
    }
}
