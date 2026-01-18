INSERT OR IGNORE INTO class_table (id_class, class_level, class_branch, class_name) VALUES 
(1, 3, 'TI', 'L3 TI'),
(2, 2, 'TI', 'L2 TI'),
(3, 3, 'CS', 'L3 Computer Science'),
(4, 1, 'TI', 'L1 TI'),
(5, 2, 'CS', 'L2 Computer Science'),
(6, 1, 'CS', 'L1 Computer Science'),
(7, 3, 'GL', 'L3 Software Engineering'),
(8, 2, 'GL', 'L2 Software Engineering');

INSERT OR IGNORE INTO teacher_table (id_teacher, first_name, last_name, user_name, user_password) VALUES 
(1, 'Ahmed', 'Professor', 'prof', '123'),
(2, 'Sarah', 'Instructor', 'teacher', '123'),
(3, 'John', 'Doe', 'john', '123'),
(4, 'Emily', 'Smith', 'emily', '123');

INSERT OR IGNORE INTO admin_table (id_admin, first_name, last_name, user_name, user_password) VALUES 
(1, 'System', 'Admin', 'admin', 'admin');

INSERT OR IGNORE INTO subject_table (id_subject, subject_name) VALUES 
(1, 'Java Programming'),
(2, 'Database Systems'),
(3, 'Algorithms'),
(4, 'Web Development'),
(5, 'Data Structures'),
(6, 'Operating Systems'),
(7, 'Software Engineering'),
(8, 'Artificial Intelligence');

INSERT OR IGNORE INTO student_table (id_student, id_class, first_name, last_name, user_name, user_password) VALUES 
(1, 1, 'Mohamed', 'Ben Ali', 'ali', '123'),
(2, 1, 'Sarra', 'Tounsi', 'sarra', '123'),
(3, 2, 'Ahmed', 'Khalil', 'ahmed', '123'),
(4, 1, 'Fatima', 'Mansour', 'fatima', '123'),
(5, 3, 'Omar', 'Jaziri', 'omar', '123'),
(6, 3, 'Youssef', 'Chouikha', 'youssef', '123'),
(7, 7, 'Leila', 'Trabelsi', 'leila', '123'),  -- Class 7 (L3 GL)
(8, 7, 'Amine', 'Gharbi', 'amine', '123'),     -- Class 7 (L3 GL)
(9, 4, 'Hana', 'Mejri', 'hana', '123'),         -- Class 4 (L1 TI)
(10, 4, 'Karim', 'Saidi', 'karim', '123');      -- Class 4 (L1 TI)

INSERT OR IGNORE INTO correspondence_table (id_teacher, id_subject, id_class) VALUES 
(1, 1, 1),  -- Teacher Ahmed teaches Java to L3 TI
(1, 2, 1),  -- Teacher Ahmed teaches Database to L3 TI
(2, 3, 2),  -- Teacher Sarah teaches Algorithms to L2 TI
(2, 4, 1),  -- Teacher Sarah teaches Web Dev to L3 TI
(3, 6, 3),  -- Teacher John teaches OS to L3 CS
(3, 5, 2),  -- Teacher John teaches Data Structures to L2 TI
(4, 7, 7),  -- Teacher Emily teaches SE to L3 GL
(4, 8, 3);  -- Teacher Emily teaches AI to L3 CS

INSERT OR IGNORE INTO absence_table (id_student, id_teacher, id_subject, absence_count, absence_date) VALUES 
(1, 1, 1, 1, '2024-01-15'),  -- Mohamed absent in Java
(2, 1, 2, 2, '2024-01-16'),  -- Sarra absent in Database (2 sessions)
(1, 2, 4, 1, '2024-01-17'),  -- Mohamed absent in Web Dev
(3, 2, 3, 1, '2024-01-18'),  -- Ahmed absent in Algorithms
(5, 3, 6, 2, '2024-01-19'),  -- Omar absent in OS
(7, 4, 7, 1, '2024-01-20'),  -- Leila absent in SE
(9, 1, 1, 1, '2024-01-21');  -- Hana absent in Java

INSERT OR IGNORE INTO admin_email_table (id_email, id_admin, id_student, subject, body, sent_at, is_read) VALUES 
(1, 1, 1, 'Absence Warning', 'Dear Mohamed, you have been absent from Java Programming class. Please ensure you catch up with the missed material.', datetime('2024-01-16 10:00:00'), 0),
(2, 1, 2, 'Multiple Absences Alert', 'Dear Sarra, we noticed you have been absent from Database Systems class for 2 sessions. Please contact your teacher to discuss this matter.', datetime('2024-01-17 09:30:00'), 1);
