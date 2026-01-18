package com.miniproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    // Update these credentials as per your local setup
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_absences";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Ensure the driver is loaded
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database successfully.");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Database Connection Failed! Make sure MySQL is running and Connector/J is in classpath.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
