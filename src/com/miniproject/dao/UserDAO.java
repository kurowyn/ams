package com.miniproject.dao;

import com.miniproject.database.DBConnection;
import com.miniproject.model.*;
import java.sql.*;

public class UserDAO {

    public User authenticate(String userName, String password, String role) {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return null;

        try {
            if ("STUDENT".equals(role)) {
                // student_table: id_student, id_class, first_name, last_name, user_name,
                // user_password
                String sql = "SELECT * FROM student_table WHERE user_name = ? AND user_password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id_student"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getInt("id_class"));
                }
            } else if ("TEACHER".equals(role)) {
                // teacher_table
                String sql = "SELECT * FROM teacher_table WHERE user_name = ? AND user_password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Teacher(
                            rs.getInt("id_teacher"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("user_name"),
                            rs.getString("user_password"));
                }
            } else if ("ADMIN".equals(role)) {
                // admin_table
                String sql = "SELECT * FROM admin_table WHERE user_name = ? AND user_password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Admin(
                            rs.getInt("id_admin"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("user_name"),
                            rs.getString("user_password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
