package com.miniproject.ui;

import com.miniproject.dao.EmailDAO;
import com.miniproject.model.Email;
import com.miniproject.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewEmailsPanel extends JPanel {
    private EmailDAO emailDAO;
    private User currentUser;
    private JTable emailTable;
    private DefaultTableModel tableModel;
    private JTextArea emailContentArea;
    private List<Email> emails;

    public ViewEmailsPanel(User user) {
        this.currentUser = user;
        this.emailDAO = new EmailDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Sent Emails", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Split pane for email list and content
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(250);

        // Email list table
        String[] columns = { "ID", "To Student", "Subject", "Date", "Read" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        emailTable = new JTable(tableModel);
        emailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emailTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySelectedEmail();
            }
        });

        // Set column widths
        emailTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        emailTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        emailTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        emailTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        emailTable.getColumnModel().getColumn(4).setPreferredWidth(80);

        JScrollPane tableScrollPane = new JScrollPane(emailTable);
        splitPane.setTopComponent(tableScrollPane);

        // Email content area
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("Email Content"));

        emailContentArea = new JTextArea();
        emailContentArea.setEditable(false);
        emailContentArea.setLineWrap(true);
        emailContentArea.setWrapStyleWord(true);
        emailContentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        emailContentArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane contentScrollPane = new JScrollPane(emailContentArea);
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);

        splitPane.setBottomComponent(contentPanel);
        add(splitPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 123, 255));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadEmails());
        buttonPanel.add(refreshButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteSelectedEmail());
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load emails
        loadEmails();
    }

    private void loadEmails() {
        tableModel.setRowCount(0);

        // Admin can see all emails or just their own
        if ("ADMIN".equals(currentUser.getRole())) {
            emails = emailDAO.getAllEmails();
        } else {
            emails = emailDAO.getEmailsByAdmin(currentUser.getId());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Email email : emails) {
            tableModel.addRow(new Object[] {
                    email.getIdEmail(),
                    email.getStudentName(),
                    email.getSubject(),
                    dateFormat.format(email.getSentAt()),
                    email.isRead() ? "Yes" : "No"
            });
        }

        // Clear content area
        emailContentArea.setText("");
    }

    private void displaySelectedEmail() {
        int selectedRow = emailTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < emails.size()) {
            Email email = emails.get(selectedRow);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            StringBuilder content = new StringBuilder();
            content.append("Email ID: ").append(email.getIdEmail()).append("\n");
            content.append("To: ").append(email.getStudentName()).append("\n");
            content.append("From: ").append(email.getAdminName()).append("\n");
            content.append("Date: ").append(dateFormat.format(email.getSentAt())).append("\n");
            content.append("Subject: ").append(email.getSubject()).append("\n");
            content.append("Status: ").append(email.isRead() ? "Read" : "Unread").append("\n");
            content.append("\n");
            content.append("─".repeat(50)).append("\n\n");
            content.append(email.getBody());

            emailContentArea.setText(content.toString());
            emailContentArea.setCaretPosition(0);
        }
    }

    private void deleteSelectedEmail() {
        int selectedRow = emailTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < emails.size()) {
            Email email = emails.get(selectedRow);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this email?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = emailDAO.deleteEmail(email.getIdEmail());
                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Email deleted successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadEmails();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to delete email.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select an email to delete.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
