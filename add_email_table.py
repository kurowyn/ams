import sqlite3
import sys

def add_email_table():
    """Add the email table to the existing database"""
    
    db_path = r"c:\Users\HP\Desktop\min project v2\gestion_absence.db"
    
    try:
        # Connect to database
        conn = sqlite3.connect(db_path)
        cursor = conn.cursor()
        
        print("Connected to database:", db_path)
        
        # Create email table
        create_table_sql = """
        CREATE TABLE IF NOT EXISTS admin_email_table (
            id_email     INTEGER PRIMARY KEY AUTOINCREMENT,
            id_admin     INTEGER NOT NULL,
            id_student   INTEGER NOT NULL,
            subject      TEXT    NOT NULL,
            body         TEXT    NOT NULL,
            sent_at      TEXT    NOT NULL DEFAULT (datetime('now')),
            is_read      INTEGER NOT NULL DEFAULT 0,
            FOREIGN KEY (id_admin) REFERENCES admin_table(id_admin) ON DELETE RESTRICT,
            FOREIGN KEY (id_student) REFERENCES student_table(id_student) ON DELETE RESTRICT,
            CHECK (is_read IN (0, 1))
        )
        """
        
        cursor.execute(create_table_sql)
        print("✓ Email table created successfully")
        
        # Create indexes
        cursor.execute("CREATE INDEX IF NOT EXISTS idx_email_admin ON admin_email_table(id_admin)")
        print("✓ Admin index created")
        
        cursor.execute("CREATE INDEX IF NOT EXISTS idx_email_student ON admin_email_table(id_student)")
        print("✓ Student index created")
        
        # Add sample data
        sample_emails = [
            (1, 1, 1, 'Absence Warning', 
             'Dear Mohamed, you have been absent from Java Programming class. Please ensure you catch up with the missed material.',
             '2024-01-16 10:00:00', 0),
            (2, 1, 2, 'Multiple Absences Alert',
             'Dear Sarra, we noticed you have been absent from Database Systems class for 2 sessions. Please contact your teacher to discuss this matter.',
             '2024-01-17 09:30:00', 1)
        ]
        
        cursor.executemany(
            "INSERT OR IGNORE INTO admin_email_table (id_email, id_admin, id_student, subject, body, sent_at, is_read) VALUES (?, ?, ?, ?, ?, ?, ?)",
            sample_emails
        )
        print("✓ Sample emails added")
        
        # Commit changes
        conn.commit()
        
        # Verify table exists
        cursor.execute("SELECT name FROM sqlite_master WHERE type='table' AND name='admin_email_table'")
        if cursor.fetchone():
            print("\n✓✓✓ Email table setup completed successfully! ✓✓✓")
            
            # Show table info
            cursor.execute("SELECT COUNT(*) FROM admin_email_table")
            count = cursor.fetchone()[0]
            print(f"Email table now contains {count} emails")
        else:
            print("Error: Email table was not created")
            
        conn.close()
        
    except sqlite3.Error as e:
        print(f"Database error: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"Error: {e}")
        sys.exit(1)

if __name__ == "__main__":
    print("=" * 60)
    print("Adding Email Table to Database")
    print("=" * 60)
    add_email_table()
    print("=" * 60)
    print("\nYou can now use the email functionality in your application!")
    print("- Admins can send emails from the 'Send Email' tab")
    print("- Students can view emails in the 'My Emails' tab")
