package com.miniproject.model;

public class Teacher extends User {
    public Teacher(int id, String firstName, String lastName, String userName, String password) {
        super(id, firstName, lastName, userName, password, "TEACHER");
    }
}
