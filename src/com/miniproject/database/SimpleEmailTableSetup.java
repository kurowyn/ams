package com.miniproject.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple utility to add the email table to the existing database
 * Run this once to add email functionality
 */
public class SimpleEmailTableSetup {

    public static void main(String[] args) {
        System.out.println("Adding email table to database...");

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to connect to database!");
                return;
            }

            Statement stmt = conn.createStatement();

            // Create email table
            String createTable = "CREATE TABLE IF NOT EXISTS admin_email_table (" +
                    "id_email INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_admin INTEGER NOT NULL, " +
                    "id_student INTEGER NOT NULL, " +
                    "subject TEXT NOT NULL, " +
                    "body TEXT NOT NULL, " +
                    "sent_at TEXT NOT NULL DEFAULT (datetime('now')), " +
                    "is_read INTEGER NOT NULL DEFAULT 0, " +
                    "FOREIGN KEY (id_admin) REFERENCES admin_table(id_admin) ON DELETE RESTRICT, " +
                    "FOREIGN KEY (id_student) REFERENCES student_table(id_student) ON DELETE RESTRICT, " +
                    "CHECK (is_read IN (0, 1)))";

            stmt.execute(createTable);
            System.out.println("✓ Email table created successfully");

            // Create indexes
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_email_admin ON admin_email_table(id_admin)");
            System.out.println("✓ Admin index created");

            stmt.execute("CREATE INDEX IF NOT EXISTS idx_email_student ON admin_email_table(id_student)");
            System.out.println("✓ Student index created");

            // Add sample data
            String sampleEmail1 = "INSERT OR IGNORE INTO admin_email_table " +
                    "(id_email, id_admin, id_student, subject, body, sent_at, is_read) VALUES " +
                    "(1, 1, 1, 'Absence Warning', " +
                    "'Dear Mohamed, you have been absent from Java Programming class. Please ensure you catch up with the missed material.', "
                    +
                    "datetime('2024-01-16 10:00:00'), 0)";

            String sampleEmail2 = "INSERT OR IGNORE INTO admin_email_table " +
                    "(id_email, id_admin, id_student, subject, body, sent_at, is_read) VALUES " +
                    "(2, 1, 2, 'Multiple Absences Alert', " +
                    "'Dear Sarra, we noticed you have been absent from Database Systems class for 2 sessions. Please contact your teacher to discuss this matter.', "
                    +
                    "datetime('2024-01-17 09:30:00'), 1)";

            stmt.execute(sampleEmail1);
            stmt.execute(sampleEmail2);
            System.out.println("✓ Sample emails added");

            System.out.println("\n✓✓✓ Email table setup completed successfully! ✓✓✓");
            System.out.println("You can now use the email functionality in the application.");

        } catch (SQLException e) {
            System.err.println("Error setting up email table!");
            e.printStackTrace();
        }
    }
}
