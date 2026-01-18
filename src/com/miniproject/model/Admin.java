package com.miniproject.model;

public class Admin extends User {
    public Admin(int id, String firstName, String lastName, String userName, String password) {
        super(id, firstName, lastName, userName, password, "ADMIN");
    }
}
