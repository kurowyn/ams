# Student Absence Management System

A Java Swing desktop application for managing student absences in educational institutions with role-based access control.

## Quick Start

### 1. Initialize Database
```bash
python init_database.py
```

### 2. Run Application
```bash
mvn exec:java
```

### 3. Login
- **Admin**: username: `admin`, password: `admin`
- **Teacher**: username: `prof`, password: `123`
- **Student**: username: `ali`, password: `123`

## Features

### 👨‍🎓 Students
- View personal absence records
- Print absence reports

### 👨‍🏫 Teachers
- View all absences
- Record student absences
- Select class, subject, and date
- Mark multiple students absent

### 👨‍💼 Administrators
- View all absences
- Manage absence records (delete/cancel)
- View statistics by subject
- Send alert notifications

## Technology Stack

- **Java 17** - Core language
- **Java Swing** - UI framework
- **SQLite** - Database
- **Maven** - Build tool

## Project Structure

```
src/com/miniproject/
├── Main.java              # Entry point
├── database/
│   └── DBConnection.java  # Database connection
├── model/                 # Data models
├── dao/                   # Data access objects
└── ui/                    # User interface
```

## Database Schema

7 tables:
- `student_table` - Student accounts
- `teacher_table` - Teacher accounts
- `admin_table` - Admin accounts
- `class_table` - Class information
- `subject_table` - Subjects/courses
- `correspondence_table` - Teacher-Subject-Class assignments
- `absence_table` - Absence records

## Documentation

📖 **[COMPREHENSIVE_GUIDE.md](COMPREHENSIVE_GUIDE.md)** - Complete documentation including:
- Detailed architecture
- Database schema
- Setup instructions
- User guide
- Developer guide
- Troubleshooting
- API reference

## Troubleshooting

### Absence list is empty?
1. Check console for `[AbsenceDAO]` error messages
2. Run: `python diagnose_absence_list.py`
3. Ensure database initialized: `python init_database.py`

### Login fails?
- Verify credentials (case-sensitive)
- Check role matches user type
- Ensure database initialized

### Build fails?
```bash
mvn clean install -U
```

## Common Commands

```bash
# Check database
python check_db.py

# Diagnose issues
python diagnose_absence_list.py

# Reset database
del gestion_absence.db
python init_database.py

# Build project
mvn clean compile

# Create JAR
mvn clean package
```

## Sample Data

After running `init_database.py`, the system includes:
- 4 students across 2 classes
- 2 teachers
- 1 administrator
- 5 subjects
- 3 sample absence records

## Requirements

- Java 17+
- Maven 3.6+
- Python 3.x (for database scripts)

## License

Educational project for absence management.

---

**Version**: 2.0  
**Last Updated**: January 2026
