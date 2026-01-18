package com.model;

import java.util.Date;

public class Email {
    private int idEmail;
    private int idAdmin;
    private int idStudent;
    private String subject;
    private String body;
    private Date sentAt;
    private boolean isRead;

    // Additional fields for display
    private String adminName;
    private String studentName;

    public Email(int idEmail, int idAdmin, int idStudent, String subject, String body, Date sentAt, boolean isRead) {
        this.idEmail = idEmail;
        this.idAdmin = idAdmin;
        this.idStudent = idStudent;
        this.subject = subject;
        this.body = body;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    // Getters and Setters
    public int getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(int idEmail) {
        this.idEmail = idEmail;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
