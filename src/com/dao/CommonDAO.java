package com.dao;

import com.database.DBConnection;
import com.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommonDAO {

    public List<SchoolClass> getAllClasses() {
        List<SchoolClass> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return list;
            // class_table: id_class, class_level, class_branch, class_name
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM class_table");
            while (rs.next()) {
                list.add(new SchoolClass(
                        rs.getInt("id_class"),
                        rs.getInt("class_level"),
                        rs.getString("class_branch"),
                        rs.getString("class_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return list;
            // subject_table: id_subject, subject_name
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM subject_table");
            while (rs.next()) {
                list.add(new Subject(rs.getInt("id_subject"), rs.getString("subject_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> getStudentsByClass(int idClass) {
        List<Student> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null)
                return list;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student_table WHERE id_class = ?");
            ps.setInt(1, idClass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id_student"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getInt("id_class")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
