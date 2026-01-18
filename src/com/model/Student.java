package com.model;

public class Student extends User {
    private int idClass;

    public Student(int id, String firstName, String lastName, String userName, String password, int idClass) {
        super(id, firstName, lastName, userName, password, "STUDENT");
        this.idClass = idClass;
    }

    public int getIdClass() {
        return idClass;
    }
}
