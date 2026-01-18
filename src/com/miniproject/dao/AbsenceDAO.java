package com.miniproject.dao;

import com.miniproject.database.DBConnection;
import com.miniproject.model.Absence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {

    public boolean addAbsence(Absence absence) {
        try {
            System.out.println("[AbsenceDAO] Adding absence for student ID: " + absence.getIdStudent());

            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("[AbsenceDAO] ERROR: Cannot add absence - connection is null");
                return false;
            }

            // absence_table: id_student, id_teacher, id_subject, absence_count,
            // absence_date
            String sql = "INSERT INTO absence_table (id_student, id_teacher, id_subject, absence_count, absence_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, absence.getIdStudent());
            ps.setInt(2, absence.getIdTeacher());
            ps.setInt(3, absence.getIdSubject());
            ps.setInt(4, absence.getCount());
            ps.setDate(5, new java.sql.Date(absence.getDate().getTime()));

            int result = ps.executeUpdate();
            boolean success = result > 0;

            if (success) {
                System.out.println("[AbsenceDAO] Absence added successfully");
            } else {
                System.err.println("[AbsenceDAO] Failed to add absence - no rows affected");
            }

            return success;
        } catch (SQLException e) {
            System.err.println("[AbsenceDAO] SQL ERROR adding absence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Absence> getAllAbsences() {
        List<Absence> list = new ArrayList<>();
        Connection conn = null;

        try {
            System.out.println("[AbsenceDAO] Attempting to load absences...");

            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("[AbsenceDAO] ERROR: Database connection is null!");
                System.err.println("  → Check DBConnection.java configuration");
                System.err.println("  → Verify gestion_absence.db exists");
                return list;
            }

            System.out.println("[AbsenceDAO] Database connection successful");

            // Join with student_table and subject_table to get names
            String sql = "SELECT a.*, s.first_name, s.last_name, sub.subject_name " +
                    "FROM absence_table a " +
                    "JOIN student_table s ON a.id_student = s.id_student " +
                    "JOIN subject_table sub ON a.id_subject = sub.id_subject";

            System.out.println("[AbsenceDAO] Executing query...");
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                try {
                    Absence abs = new Absence(
                            rs.getInt("id_student"),
                            rs.getInt("id_teacher"),
                            rs.getInt("id_subject"),
                            rs.getDate("absence_date"),
                            rs.getInt("absence_count"));

                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String subjectName = rs.getString("subject_name");

                    // Null safety checks
                    if (firstName == null)
                        firstName = "Unknown";
                    if (lastName == null)
                        lastName = "Student";
                    if (subjectName == null)
                        subjectName = "Unknown Subject";

                    abs.setStudentName(firstName + " " + lastName);
                    abs.setSubjectName(subjectName);
                    list.add(abs);
                    count++;
                } catch (SQLException e) {
                    System.err.println("[AbsenceDAO] Error reading row: " + e.getMessage());
                }
            }

            System.out.println("[AbsenceDAO] Successfully loaded " + count + " absence records");

        } catch (SQLException e) {
            System.err.println("[AbsenceDAO] SQL ERROR: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  SQL State: " + e.getSQLState());
            System.err.println("\nPossible causes:");
            System.err.println("  1. Missing tables (student_table, subject_table, absence_table)");
            System.err.println("     → Run: python init_database.py");
            System.err.println("  2. Incorrect database schema");
            System.err.println("  3. Database file corrupted");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("[AbsenceDAO] Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // Deleting is trickier with composite PK, need all fields
    public boolean deleteAbsence(int idStudent, int idSubject, java.util.Date date) {
        try {
            System.out.println("[AbsenceDAO] Deleting absence for student ID: " + idStudent);

            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("[AbsenceDAO] ERROR: Cannot delete - connection is null");
                return false;
            }

            String sql = "DELETE FROM absence_table WHERE id_student = ? AND id_subject = ? AND absence_date = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idStudent);
            ps.setInt(2, idSubject);
            ps.setDate(3, new java.sql.Date(date.getTime()));

            int result = ps.executeUpdate();
            boolean success = result > 0;

            if (success) {
                System.out.println("[AbsenceDAO] Absence deleted successfully");
            } else {
                System.err.println("[AbsenceDAO] No absence found to delete");
            }

            return success;
        } catch (SQLException e) {
            System.err.println("[AbsenceDAO] SQL ERROR deleting absence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
