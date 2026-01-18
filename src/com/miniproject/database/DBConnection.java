package com.miniproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    // SQLite database file path
    private static final String DB_PATH = "gestion_absence.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    // Load SQLite JDBC driver
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection(URL);
                    System.out.println("Connected to SQLite database successfully: " + DB_PATH);
                } catch (ClassNotFoundException e) {
                    System.err.println("Database Driver NOT FOUND! Make sure sqlite-jdbc is in classpath.");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection status: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
