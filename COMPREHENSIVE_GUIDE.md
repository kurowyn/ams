# Student Absence Management System - Comprehensive Guide

## Table of Contents
1. [Project Overview](#project-overview)
2. [System Architecture](#system-architecture)
3. [Database Schema](#database-schema)
4. [Setup & Installation](#setup--installation)
5. [User Guide](#user-guide)
6. [Developer Guide](#developer-guide)
7. [Troubleshooting](#troubleshooting)
8. [API Reference](#api-reference)

---

## Project Overview

### Description
A Java Swing desktop application for managing student absences in an educational institution. The system supports three user roles: Students, Teachers, and Administrators.

### Key Features
- ✅ Role-based authentication (Student, Teacher, Admin)
- ✅ Record and track student absences
- ✅ View absence history with filtering
- ✅ Generate absence statistics
- ✅ Admin panel for absence management
- ✅ SQLite database for data persistence

### Technology Stack
- **Language**: Java 17
- **UI Framework**: Java Swing
- **Database**: SQLite
- **Build Tool**: Maven
- **JDBC Driver**: sqlite-jdbc 3.45.0.0

---

## System Architecture

### Project Structure
```
min project v2/
├── src/com/miniproject/
│   ├── Main.java                    # Application entry point
│   ├── database/
│   │   └── DBConnection.java        # Database connection manager
│   ├── model/
│   │   ├── User.java                # Abstract user class
│   │   ├── Student.java             # Student model
│   │   ├── Teacher.java             # Teacher model
│   │   ├── Admin.java               # Admin model
│   │   ├── SchoolClass.java         # Class model
│   │   ├── Subject.java             # Subject model
│   │   └── Absence.java             # Absence record model
│   ├── dao/
│   │   ├── UserDAO.java             # User authentication
│   │   ├── CommonDAO.java           # Class/Subject queries
│   │   └── AbsenceDAO.java          # Absence CRUD operations
│   └── ui/
│       ├── LoginFrame.java          # Login screen
│       ├── MainDashboard.java       # Main application window
│       ├── AbsenceListPanel.java    # View absences
│       ├── AddAbsencePanel.java     # Record absences (Teacher)
│       ├── AdminPanel.java          # Admin management
│       └── StatsPanel.java          # Statistics visualization
├── gestion_absence.db               # SQLite database file
├── database_setup_sqlite.sql        # Database schema (SQLite)
├── init_database.py                 # Database initialization script
├── pom.xml                          # Maven configuration
└── README.md                        # Project documentation
```

### Design Patterns
- **DAO Pattern**: Separates data access logic from business logic
- **MVC Pattern**: Models, Views (UI), Controllers (DAOs)
- **Singleton Pattern**: DBConnection for single database connection

---

## Database Schema

### Tables Overview
The system uses **7 tables**:

#### 1. `class_table`
Stores class/grade information.
```sql
CREATE TABLE class_table (
    id_class     INTEGER PRIMARY KEY AUTOINCREMENT,
    class_level  INTEGER NOT NULL,
    class_branch TEXT NOT NULL,
    class_name   TEXT NOT NULL,
    UNIQUE (class_level, class_branch)
);
```

#### 2. `student_table`
Student accounts and information.
```sql
CREATE TABLE student_table (
    id_student    INTEGER PRIMARY KEY AUTOINCREMENT,
    id_class      INTEGER NOT NULL,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL,
    FOREIGN KEY (id_class) REFERENCES class_table(id_class)
);
```

#### 3. `teacher_table`
Teacher accounts.
```sql
CREATE TABLE teacher_table (
    id_teacher    INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);
```

#### 4. `admin_table`
Administrator accounts.
```sql
CREATE TABLE admin_table (
    id_admin      INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    user_name     TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL
);
```

#### 5. `subject_table`
Academic subjects/courses.
```sql
CREATE TABLE subject_table (
    id_subject   INTEGER PRIMARY KEY AUTOINCREMENT,
    subject_name TEXT NOT NULL UNIQUE
);
```

#### 6. `correspondence_table`
Links teachers to subjects and classes (who teaches what to whom).
```sql
CREATE TABLE correspondence_table (
    id_teacher INTEGER NOT NULL,
    id_subject INTEGER NOT NULL,
    id_class   INTEGER NOT NULL,
    PRIMARY KEY (id_teacher, id_subject, id_class),
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher),
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject),
    FOREIGN KEY (id_class) REFERENCES class_table(id_class)
);
```

#### 7. `absence_table`
Absence records (composite primary key).
```sql
CREATE TABLE absence_table (
    id_student    INTEGER NOT NULL,
    id_teacher    INTEGER NOT NULL,
    id_subject    INTEGER NOT NULL,
    absence_count INTEGER NOT NULL DEFAULT 1,
    absence_date  DATE NOT NULL,
    PRIMARY KEY (id_student, id_subject, absence_date),
    FOREIGN KEY (id_student) REFERENCES student_table(id_student),
    FOREIGN KEY (id_teacher) REFERENCES teacher_table(id_teacher),
    FOREIGN KEY (id_subject) REFERENCES subject_table(id_subject)
);
```

### Entity Relationships
```
class_table (1) ──→ (N) student_table
teacher_table (1) ──→ (N) correspondence_table (N) ←── (1) subject_table
                                    ↓ (N)
                              class_table (1)
student_table (1) ──→ (N) absence_table (N) ←── (1) subject_table
teacher_table (1) ──→ (N) absence_table
```

---

## Setup & Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Python 3.x (for database initialization)

### Step 1: Clone/Download Project
```bash
cd "c:\Users\HP\Desktop\min project v2"
```

### Step 2: Initialize Database
```bash
python init_database.py
```

This creates all 7 tables and inserts sample data.

### Step 3: Verify Database
```bash
python check_db.py
```

Expected output:
```
Total tables: 7
1. absence_table          - 3 rows
2. admin_table            - 1 rows
3. class_table            - 4 rows
4. correspondence_table   - 4 rows
5. student_table          - 4 rows
6. subject_table          - 5 rows
7. teacher_table          - 2 rows
```

### Step 4: Build Project
```bash
mvn clean compile
```

### Step 5: Run Application
```bash
mvn exec:java
```

Or use the batch file:
```bash
run.bat
```

---

## User Guide

### Login Credentials (Sample Data)

#### Students
| Username | Password | Name | Class |
|----------|----------|------|-------|
| ali | 123 | Mohamed Ben Ali | L3 TI |
| sarra | 123 | Sarra Tounsi | L3 TI |
| ahmed | 123 | Ahmed Khalil | L2 TI |
| fatima | 123 | Fatima Mansour | L3 TI |

#### Teachers
| Username | Password | Name |
|----------|----------|------|
| prof | 123 | Ahmed Professor |
| teacher | 123 | Sarah Instructor |

#### Administrators
| Username | Password | Name |
|----------|----------|------|
| admin | admin | System Admin |

### Features by Role

#### Student Features
- **View Absences**: See only their own absence records
- **Print**: Print absence report

#### Teacher Features
- **View Absences**: See all absences in the system
- **Record Absence**: Mark students as absent
  1. Select class
  2. Select subject
  3. Select date
  4. Click "Load Students"
  5. Check absent students
  6. Click "Save Absences"

#### Admin Features
- **View Absences**: See all absences
- **Administration Panel**:
  - View detailed absence records
  - Cancel/delete absences
  - Send alert emails (simulated)
- **Statistics**: View absence statistics by subject

---

## Developer Guide

### Adding a New User
```java
// Example: Add a new student
Student newStudent = new Student(
    0,              // ID (auto-generated)
    "John",         // First name
    "Doe",          // Last name
    "john",         // Username
    "password",     // Password
    1               // Class ID
);
```

### Database Connection
```java
// Get connection
Connection conn = DBConnection.getConnection();

// Connection is singleton - reused across application
// SQLite URL: jdbc:sqlite:gestion_absence.db
```

### Adding New Features

#### 1. Create Model Class
```java
public class NewModel {
    private int id;
    private String name;
    
    // Constructor, getters, setters
}
```

#### 2. Create DAO Class
```java
public class NewModelDAO {
    public List<NewModel> getAll() {
        // Database query logic
    }
    
    public boolean add(NewModel model) {
        // Insert logic
    }
}
```

#### 3. Create UI Panel
```java
public class NewPanel extends JPanel {
    public NewPanel(User user) {
        // UI components
    }
}
```

#### 4. Add to MainDashboard
```java
tabbedPane.addTab("New Feature", new NewPanel(user));
```

### Code Style Guidelines
- Use descriptive variable names
- Add logging for debugging: `System.out.println("[ClassName] Message")`
- Handle null checks
- Use try-catch for database operations
- Follow existing naming conventions

---

## Troubleshooting

### Problem: Absence list is empty

**Diagnosis:**
1. Check console for `[AbsenceDAO]` messages
2. Run `python diagnose_absence_list.py`

**Common Causes:**
- **Missing tables**: Run `python init_database.py`
- **No data**: Record test absences as teacher
- **Database connection failed**: Check `gestion_absence.db` exists

**Console Messages:**
```
[AbsenceDAO] ERROR: Database connection is null!
→ Database file missing or path incorrect

[AbsenceDAO] SQL ERROR: no such table: student_table
→ Run: python init_database.py

[AbsenceDAO] Successfully loaded 0 absence records
→ No absences recorded yet (normal)
```

### Problem: Login fails

**Check:**
- Username/password correct (case-sensitive)
- Role matches user type
- Database initialized with sample users

**Test with:**
- Username: `admin`, Password: `admin`, Role: `ADMIN`

### Problem: "Record Absence" tab missing

**Cause:** User logged in as STUDENT (students can't record absences)

**Solution:** Login as TEACHER or ADMIN

### Problem: Database locked error

**Cause:** Multiple connections or file in use

**Solution:**
1. Close all application instances
2. Restart application
3. If persists, delete `gestion_absence.db` and reinitialize

### Problem: Maven build fails

**Check:**
1. Java version: `java -version` (should be 17+)
2. Maven version: `mvn -version` (should be 3.6+)
3. Internet connection (for downloading dependencies)

**Solution:**
```bash
mvn clean install -U
```

---

## API Reference

### DBConnection
```java
public static Connection getConnection()
```
Returns singleton SQLite database connection.

### UserDAO
```java
public User authenticate(String userName, String password, String role)
```
Authenticates user and returns User object (Student/Teacher/Admin) or null.

### CommonDAO
```java
public List<SchoolClass> getAllClasses()
public List<Subject> getAllSubjects()
public List<Student> getStudentsByClass(int idClass)
```

### AbsenceDAO
```java
public boolean addAbsence(Absence absence)
public List<Absence> getAllAbsences()
public boolean deleteAbsence(int idStudent, int idSubject, Date date)
```

### Models

#### User (Abstract)
- `int getId()`
- `String getFirstName()`
- `String getLastName()`
- `String getUserName()`
- `String getRole()` // "STUDENT", "TEACHER", or "ADMIN"

#### Student extends User
- `int getIdClass()`

#### Absence
- `int getIdStudent()`
- `int getIdTeacher()`
- `int getIdSubject()`
- `Date getDate()`
- `int getCount()`
- `String getStudentName()` // Populated by DAO
- `String getSubjectName()` // Populated by DAO

---

## Project Maintenance

### Backup Database
```bash
copy gestion_absence.db gestion_absence.db.backup
```

### Reset Database
```bash
del gestion_absence.db
python init_database.py
```

### Update Dependencies
Edit `pom.xml` and run:
```bash
mvn clean install
```

### Build JAR File
```bash
mvn clean package
```
Output: `target/gestion-absences-1.0-SNAPSHOT.jar`

---

## Future Enhancements

### Potential Features
- [ ] Email notifications for excessive absences
- [ ] Export to PDF/Excel
- [ ] Absence justification system
- [ ] Multi-language support
- [ ] Web-based interface
- [ ] Password encryption
- [ ] User management UI (add/edit/delete users)
- [ ] Attendance percentage calculation
- [ ] Academic year management
- [ ] Backup/restore functionality

### Security Improvements
- [ ] Hash passwords (currently plain text)
- [ ] Session management
- [ ] Input validation
- [ ] SQL injection prevention (use PreparedStatement - already done)
- [ ] Role-based access control refinement

---

## Support & Contact

### Getting Help
1. Check console output for diagnostic messages
2. Run diagnostic scripts (`check_db.py`, `diagnose_absence_list.py`)
3. Review this guide's troubleshooting section
4. Check error stack traces in console

### Common Commands
```bash
# Check database
python check_db.py

# Diagnose absence list issues
python diagnose_absence_list.py

# Initialize/reset database
python init_database.py

# Run application
mvn exec:java

# Build project
mvn clean compile

# Package as JAR
mvn clean package
```

---

## License & Credits

**Project**: Student Absence Management System  
**Version**: 2.0  
**Database**: SQLite  
**UI Framework**: Java Swing  
**Build Tool**: Maven

---

*Last Updated: January 2026*
