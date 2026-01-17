package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_absences";
    private static final String USER = "root";
    private static final String PWD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (Exception e) {
            System.out.println("Database connection failed");
            return null;
        }
    }
}
