package com.miniproject.dao;

import com.miniproject.database.DBConnection;
import com.miniproject.model.Absence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {

    public boolean addAbsence(Absence absence) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return false;
            // absence_table: id_student, id_teacher, id_subject, absence_count,
            // absence_date
            String sql = "INSERT INTO absence_table (id_student, id_teacher, id_subject, absence_count, absence_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, absence.getIdStudent());
            ps.setInt(2, absence.getIdTeacher());
            ps.setInt(3, absence.getIdSubject());
            ps.setInt(4, absence.getCount());
            ps.setDate(5, new java.sql.Date(absence.getDate().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Absence> getAllAbsences() {
        List<Absence> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return list;

            // Join with student_table and subject_table to get names
            String sql = "SELECT a.*, s.first_name, s.last_name, sub.subject_name " +
                    "FROM absence_table a " +
                    "JOIN student_table s ON a.id_student = s.id_student " +
                    "JOIN subject_table sub ON a.id_subject = sub.id_subject";

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Absence abs = new Absence(
                        rs.getInt("id_student"),
                        rs.getInt("id_teacher"),
                        rs.getInt("id_subject"),
                        rs.getDate("absence_date"),
                        rs.getInt("absence_count"));
                abs.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                abs.setSubjectName(rs.getString("subject_name"));
                list.add(abs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Deleting is trickier with composite PK, need all fields
    public boolean deleteAbsence(int idStudent, int idSubject, java.util.Date date) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return false;
            String sql = "DELETE FROM absence_table WHERE id_student = ? AND id_subject = ? AND absence_date = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idStudent);
            ps.setInt(2, idSubject);
            ps.setDate(3, new java.sql.Date(date.getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
