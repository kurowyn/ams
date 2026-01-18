package com.dao;

import com.database.DBConnection;
import com.model.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {

    /**
     * Send an email from admin to student
     */
    public boolean sendEmail(int idAdmin, int idStudent, String subject, String body) {
        String sql = "INSERT INTO admin_email_table (id_admin, id_student, subject, body) VALUES (?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdmin);
            stmt.setInt(2, idStudent);
            stmt.setString(3, subject);
            stmt.setString(4, body);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all emails sent by a specific admin
     */
    public List<Email> getEmailsByAdmin(int idAdmin) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT e.id_email, e.id_admin, e.id_student, e.subject, e.body, " +
                "e.sent_at, e.is_read, " +
                "s.first_name || ' ' || s.last_name AS student_name " +
                "FROM admin_email_table e " +
                "JOIN student_table s ON e.id_student = s.id_student " +
                "WHERE e.id_admin = ? " +
                "ORDER BY e.sent_at DESC";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdmin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Email email = new Email(
                        rs.getInt("id_email"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_student"),
                        rs.getString("subject"),
                        rs.getString("body"),
                        rs.getTimestamp("sent_at"),
                        rs.getInt("is_read") == 1);
                email.setStudentName(rs.getString("student_name"));
                emails.add(email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }

    /**
     * Get all emails received by a specific student
     */
    public List<Email> getEmailsByStudent(int idStudent) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT e.id_email, e.id_admin, e.id_student, e.subject, e.body, " +
                "e.sent_at, e.is_read, " +
                "a.first_name || ' ' || a.last_name AS admin_name " +
                "FROM admin_email_table e " +
                "JOIN admin_table a ON e.id_admin = a.id_admin " +
                "WHERE e.id_student = ? " +
                "ORDER BY e.sent_at DESC";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idStudent);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Email email = new Email(
                        rs.getInt("id_email"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_student"),
                        rs.getString("subject"),
                        rs.getString("body"),
                        rs.getTimestamp("sent_at"),
                        rs.getInt("is_read") == 1);
                email.setAdminName(rs.getString("admin_name"));
                emails.add(email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }

    /**
     * Mark an email as read
     */
    public boolean markAsRead(int idEmail) {
        String sql = "UPDATE admin_email_table SET is_read = 1 WHERE id_email = ?";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmail);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get count of unread emails for a student
     */
    public int getUnreadCount(int idStudent) {
        String sql = "SELECT COUNT(*) FROM admin_email_table WHERE id_student = ? AND is_read = 0";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idStudent);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get all emails (for admin to view all sent emails)
     */
    public List<Email> getAllEmails() {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT e.id_email, e.id_admin, e.id_student, e.subject, e.body, " +
                "e.sent_at, e.is_read, " +
                "a.first_name || ' ' || a.last_name AS admin_name, " +
                "s.first_name || ' ' || s.last_name AS student_name " +
                "FROM admin_email_table e " +
                "JOIN admin_table a ON e.id_admin = a.id_admin " +
                "JOIN student_table s ON e.id_student = s.id_student " +
                "ORDER BY e.sent_at DESC";

        Connection conn = DBConnection.getConnection();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Email email = new Email(
                        rs.getInt("id_email"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_student"),
                        rs.getString("subject"),
                        rs.getString("body"),
                        rs.getTimestamp("sent_at"),
                        rs.getInt("is_read") == 1);
                email.setAdminName(rs.getString("admin_name"));
                email.setStudentName(rs.getString("student_name"));
                emails.add(email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }

    /**
     * Delete an email
     */
    public boolean deleteEmail(int idEmail) {
        String sql = "DELETE FROM admin_email_table WHERE id_email = ?";

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmail);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
