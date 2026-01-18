package com.miniproject.model;

import java.util.Date;

public class Absence {
    // Composite Key
    private int idStudent;
    private int idTeacher;
    private int idSubject;
    private Date date;

    private int count; // absence_count

    // UI helper fields
    private String studentName;
    private String subjectName;
    private int classLevel; // Added for stats aggregation

    public Absence(int idStudent, int idTeacher, int idSubject, Date date, int count) {
        this.idStudent = idStudent;
        this.idTeacher = idTeacher;
        this.idSubject = idSubject;
        this.date = date;
        this.count = count;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }
}
