package com.miniproject.test;

import com.miniproject.database.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println(" Connection successful!");
        } else {
            System.out.println(" Connection failed!");
        }
    }
}
