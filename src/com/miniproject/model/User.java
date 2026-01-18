package com.miniproject.model;

public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;
    protected String role; // "STUDENT", "TEACHER", "ADMIN"

    public User(int id, String firstName, String lastName, String userName, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
