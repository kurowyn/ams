PRAGMA foreign_keys = ON;

CREATE TABLE class_table (
    id_class     INTEGER PRIMARY KEY,
    class_level  INTEGER NOT NULL,
    class_branch TEXT    NOT NULL,
    class_name   TEXT    NOT NULL,
    UNIQUE (class_level, class_branch)
);

create table student_table (
    id_student integer primary key,
    id_class   integer not null,
    first_name text not null,
    last_name  text not null,
    user_name  text not null unique,
    user_password text not null,
    foreign key (id_class)
    references class_table(id_class)
    on delete restrict
);

CREATE TABLE teacher_table (
    id_teacher INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    user_name  TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);

CREATE TABLE admin_table (
    id_admin INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    user_name  TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);

CREATE TABLE subject_table (
    id_subject   INTEGER PRIMARY KEY,
    subject_name TEXT NOT NULL UNIQUE
);

CREATE TABLE correspondence_table (
    id_teacher INTEGER NOT NULL,
    id_subject INTEGER NOT NULL,
    id_class   INTEGER NOT NULL,
    PRIMARY KEY (id_teacher, id_subject, id_class),
    FOREIGN KEY (id_teacher)
    REFERENCES teacher_table(id_teacher)
    ON DELETE RESTRICT,
    FOREIGN KEY (id_subject)
    REFERENCES subject_table(id_subject)
    ON DELETE RESTRICT,
    FOREIGN KEY (id_class)
    REFERENCES class_table(id_class)
    ON DELETE RESTRICT
) WITHOUT ROWID;


CREATE INDEX idx_corr_teacher ON correspondence_table(id_teacher);
CREATE INDEX idx_corr_subject ON correspondence_table(id_subject);
CREATE INDEX idx_corr_class ON correspondence_table(id_class);

CREATE TABLE absence_table (
    id_student   INTEGER NOT NULL,
    id_teacher   INTEGER NOT NULL,
    id_subject   INTEGER NOT NULL,
    absence_count INTEGER NOT NULL DEFAULT 1,
    absence_date TEXT    NOT NULL,
    PRIMARY KEY (id_student, id_subject, absence_date),
    FOREIGN KEY (id_student)
    REFERENCES student_table(id_student)
    ON DELETE RESTRICT,
    FOREIGN KEY (id_teacher)
    REFERENCES teacher_table(id_teacher)
    ON DELETE RESTRICT,
    FOREIGN KEY (id_subject)
    REFERENCES subject_table(id_subject)
    ON DELETE RESTRICT,
    CHECK (absence_count >= 1),
    CHECK (absence_date = date(absence_date))
) WITHOUT ROWID;

CREATE INDEX idx_absence_student ON absence_table(id_student);
CREATE INDEX idx_absence_teacher ON absence_table(id_teacher);
CREATE INDEX idx_absence_subject ON absence_table(id_subject);
