# Comprehensive Program Guide

This document describes the flow and steps of the **Student Absence Management System**.

## 1. Database Initialization
**File:** `database_setup.sql`
- **Step 1**: The script creates a database named `gestion_absences`.
- **Step 2**: It creates necessary tables:
  - `Classe`: Stores class levels (e.g., L3 TI).
  - `Matiere`: Stores subjects (e.g., Java, Databases).
  - `Etudiant`, `Enseignant`, `Responsable`: Store user credentials and details.
  - `Correspondance`: Links Teachers to specific Subjects and Classes.
  - `Absence`: Stores individual absence records linked to a student, subject, teacher, and date.
- **Step 3**: It populates the tables with initial test data (e.g., Student `ali`, Teacher `prof`, Admin `admin`).

## 2. Application Entry Point
**File:** `src/com/miniproject/Main.java`
- **Step**: Contains the `main` method.
- **Action**: It parses the Swing Event Dispatch Thread (EDT) and launches the `LoginFrame`.

## 3. Login Process
**File:** `src/com/miniproject/ui/LoginFrame.java`
- **Step 1 (User Input)**: The user enters `Login`, `Password` and selects a `Role` (Student, Teacher, or Admin).
- **Step 2 (Authentication)**: 
  - The app calls `UserDAO.authenticate(login, password, role)`.
  - It connects to the database via `DBConnection`.
  - It queries the specific table matching the selected role (e.g., `SELECT * FROM Etudiant...`).
- **Step 3 (Result)**:
  - **Success**: Opens `MainDashboard` passing the logged-in `User` object.
  - **Failure**: Shows an error message "Login or password incorrect".

## 4. Main Dashboard
**File:** `src/com/miniproject/ui/MainDashboard.java`
- **Step**: The dashboard dynamically creates tabs based on the User's Role.
  - **All Users**: See the "Consulter Absences" tab.
  - **Teachers**: See an extra "Saisir Absence" tab.
  - **Admins** (Responsable): See "Administration" and "Statistiques" tabs.

## 5. Functional Workflows

### A. Taking Attendance (Teachers Only)
**File:** `src/com/miniproject/ui/AddAbsencePanel.java`
1.  **Select Context**: Teacher selects a **Class** and a **Subject**.
2.  **Load Students**: Clicking "Charger Etudiants" fetches the list of students in that class from the database.
3.  **Mark Absences**: The teacher checks the box "Absent?" next to specific students.
4.  **Save**: Clicking "Enregistrer" loops through the table and inserts a new record into the `Absence` table for every checked student.

### B. Viewing Absences (Students, Teachers, Admins)
**File:** `src/com/miniproject/ui/AbsenceListPanel.java`
1.  **Load Data**: Retrieves absence records from the database.
2.  **Filter**: 
    - If the user is a **Student**, they only see *their own* absences.
    - Teachers and Admins see *all* absences.
3.  **Print**: Clicking "Imprimer" opens the system print dialog to print the table.

### C. Administration (Admins Only)
**File:** `src/com/miniproject/ui/AdminPanel.java`
1.  **View**: lists all absences.
2.  **Delete**: Admin selects a row and clicks "Annuler Absence". The system converts this to a SQL `DELETE` command to remove the record.
3.  **Alerts**: "Envoyer Mails" simulates sending warning emails to students with high absence rates.

### D. Statistics (Admins Only)
**File:** `src/com/miniproject/ui/StatsPanel.java`
1.  **Visualization**: Draws a custom bar chart.
2.  **Process**: Aggregates total absences grouped by Subject (`Matiere`) and displays the comparative volume visually.
