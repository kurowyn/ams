-- Database Setup for Student Absence Management System (Refactored)
CREATE DATABASE IF NOT EXISTS gestion_absences;
USE gestion_absences;

-- 1. Class Table
CREATE TABLE class_table (
    id_class     INT PRIMARY KEY AUTO_INCREMENT,
    class_level  INT NOT NULL,
    class_branch VARCHAR(50) NOT NULL,
    class_name   VARCHAR(50) NOT NULL,
    UNIQUE (class_level, class_branch)
);

-- 2. Student Table
CREATE TABLE student_table (
    id_student    INT PRIMARY KEY AUTO_INCREMENT,
    id_class      INT NOT NULL,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    user_name     VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_class) REFERENCES class_table(id_class) ON DELETE RESTRICT
);

-- 3. Teacher Table
CREATE TABLE teacher_table (
    id_teacher    INT PRIMARY KEY AUTO_INCREMENT,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    user_name     VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(50) NOT NULL
);

-- 4. Admin Table
CREATE TABLE admin_table (
    id_admin      INT PRIMARY KEY AUTO_INCREMENT,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    user_name     VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(50) NOT NULL
);

-- 5. Subject Table
CREATE TABLE subject_table (
    id_subject   INT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL UNIQUE
);

-- 6. Correspondence Table (Linking Teachers to Subjects and Classes)
CREATE TABLE correspondence_table (
    id_teacher INT NOT NULL,
    id_subject INT NOT NULL,
    id_class   INT NOT NULL,
    PRIMARY KEY (id_teacher, id_subject, id_class),
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher) ON DELETE RESTRICT,
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject) ON DELETE RESTRICT,
    FOREIGN KEY (id_class)   REFERENCES class_table(id_class)   ON DELETE RESTRICT
);

CREATE INDEX idx_corr_teacher ON correspondence_table(id_teacher);
CREATE INDEX idx_corr_subject ON correspondence_table(id_subject);
CREATE INDEX idx_corr_class   ON correspondence_table(id_class);

-- 7. Absence Table
CREATE TABLE absence_table (
    id_student    INT NOT NULL,
    id_teacher    INT NOT NULL,
    id_subject    INT NOT NULL,
    absence_count INT NOT NULL DEFAULT 1,
    absence_date  DATE NOT NULL,
    PRIMARY KEY (id_student, id_subject, absence_date),
    FOREIGN KEY (id_student) REFERENCES student_table(id_student) ON DELETE RESTRICT,
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher) ON DELETE RESTRICT,
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject) ON DELETE RESTRICT
);

--8. Mail
CREATE TABLE admin_email_table (
    id_email     INTEGER PRIMARY KEY,
    id_admin     INTEGER NOT NULL,
    id_student   INTEGER NOT NULL,
    body         TEXT    NOT NULL,
    sent_at      TEXT    NOT NULL DEFAULT (datetime('now')),
    is_read      INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (id_admin)
    REFERENCES admin_table(id_admin)
    ON DELETE RESTRICT,
    FOREIGN KEY (id_student)
    REFERENCES student_table(id_student)
    ON DELETE RESTRICT,
    CHECK (is_read IN (0, 1)),
    CHECK (sent_at = datetime(sent_at))
);
CREATE INDEX idx_absence_student ON absence_table(id_student);
CREATE INDEX idx_absence_teacher ON absence_table(id_teacher);
CREATE INDEX idx_absence_subject ON absence_table(id_subject);


-- INSERT SAMPLE DATA

-- Classes
INSERT INTO class_table (class_level, class_branch, class_name) VALUES 
(3, 'TI', 'L3 TI'),
(2, 'TI', 'L2 TI');

-- Students
INSERT INTO student_table (id_class, first_name, last_name, user_name, user_password) VALUES 
(1, 'Mohamed', 'Ben Ali', 'ali', '123'),
(1, 'Sarra', 'Tounsi', 'sarra', '123');

-- Teachers
INSERT INTO teacher_table (first_name, last_name, user_name, user_password) VALUES 
( 'Ahmed', 'Professeur', 'prof', '123');

-- Admins
INSERT INTO admin_table (first_name, last_name, user_name, user_password) VALUES 
('System', 'Admin', 'admin', 'admin');

-- Subjects
INSERT INTO subject_table (subject_name) VALUES ('Java'), ('Base de Données'), ('Algorithmique');

-- Correspondence
INSERT INTO correspondence_table (id_teacher, id_subject, id_class) VALUES (1, 1, 1);
