CREATE TABLE IF NOT EXISTS class_table (
    id_class     INTEGER PRIMARY KEY AUTOINCREMENT,
    class_level  INTEGER NOT NULL,
    class_branch TEXT NOT NULL,
    class_name   TEXT NOT NULL,
    UNIQUE (class_level, class_branch)
);

CREATE TABLE IF NOT EXISTS student_table (
    id_student    INTEGER PRIMARY KEY AUTOINCREMENT,
    id_class      INTEGER NOT NULL,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL,
    FOREIGN KEY (id_class) REFERENCES class_table(id_class) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS teacher_table (
    id_teacher    INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS admin_table (
    id_admin      INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS subject_table (
    id_subject   INTEGER PRIMARY KEY AUTOINCREMENT,
    subject_name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS correspondence_table (
    id_teacher INTEGER NOT NULL,
    id_subject INTEGER NOT NULL,
    id_class   INTEGER NOT NULL,
    PRIMARY KEY (id_teacher, id_subject, id_class),
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher) ON DELETE RESTRICT,
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject) ON DELETE RESTRICT,
    FOREIGN KEY (id_class)   REFERENCES class_table(id_class)   ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_corr_teacher ON correspondence_table(id_teacher);
CREATE INDEX IF NOT EXISTS idx_corr_subject ON correspondence_table(id_subject);
CREATE INDEX IF NOT EXISTS idx_corr_class   ON correspondence_table(id_class);

CREATE TABLE IF NOT EXISTS absence_table (
    id_student    INTEGER NOT NULL,
    id_teacher    INTEGER NOT NULL,
    id_subject    INTEGER NOT NULL,
    absence_count INTEGER NOT NULL DEFAULT 1,
    absence_date  DATE NOT NULL,
    PRIMARY KEY (id_student, id_subject, absence_date),
    FOREIGN KEY (id_student) REFERENCES student_table(id_student) ON DELETE RESTRICT,
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher) ON DELETE RESTRICT,
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_absence_student ON absence_table(id_student);
CREATE INDEX IF NOT EXISTS idx_absence_teacher ON absence_table(id_teacher);
CREATE INDEX IF NOT EXISTS idx_absence_subject ON absence_table(id_subject);

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
