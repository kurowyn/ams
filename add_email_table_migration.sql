-- Migration Script: Add Email Table to Existing Database
-- Run this script if you already have a database without the email table

-- 8. Admin Email Table
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
);

CREATE INDEX IF NOT EXISTS idx_email_admin ON admin_email_table(id_admin);
CREATE INDEX IF NOT EXISTS idx_email_student ON admin_email_table(id_student);

-- Sample Emails (optional)
INSERT OR IGNORE INTO admin_email_table (id_email, id_admin, id_student, subject, body, sent_at, is_read) VALUES 
(1, 1, 1, 'Absence Warning', 'Dear Mohamed, you have been absent from Java Programming class. Please ensure you catch up with the missed material.', datetime('2024-01-16 10:00:00'), 0),
(2, 1, 2, 'Multiple Absences Alert', 'Dear Sarra, we noticed you have been absent from Database Systems class for 2 sessions. Please contact your teacher to discuss this matter.', datetime('2024-01-17 09:30:00'), 1);
