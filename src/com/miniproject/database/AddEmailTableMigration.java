package com.miniproject.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to add the email table to the existing database
 * Run this once to migrate your database
 */
public class AddEmailTableMigration {

    public static void main(String[] args) {
        System.out.println("Starting email table migration...");

        String migrationFile = "add_email_table_migration.sql";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to connect to database!");
                return;
            }

            // Read the migration SQL file
            StringBuilder sql = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(migrationFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Skip comments and empty lines
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("--")) {
                        sql.append(line).append(" ");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading migration file: " + migrationFile);
                e.printStackTrace();
                return;
            }

            // Split by semicolon to execute each statement separately
            String[] statements = sql.toString().split(";");

            Statement stmt = conn.createStatement();
            int successCount = 0;

            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    try {
                        stmt.execute(statement);
                        successCount++;
                        System.out.println(
                                "Executed: " + statement.substring(0, Math.min(50, statement.length())) + "...");
                    } catch (SQLException e) {
                        System.err.println("Error executing statement: "
                                + statement.substring(0, Math.min(50, statement.length())));
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("\nMigration completed successfully!");
            System.out.println("Executed " + successCount + " statements.");
            System.out.println("Email table has been added to the database.");

        } catch (SQLException e) {
            System.err.println("Database error during migration!");
            e.printStackTrace();
        }
    }
}
