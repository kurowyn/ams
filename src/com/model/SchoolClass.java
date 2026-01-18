package com.model;

public class SchoolClass {
    private int id;
    private int level;
    private String branch;
    private String name;

    public SchoolClass(int id, int level, String branch, String name) {
        this.id = id;
        this.level = level;
        this.branch = branch;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // e.g. "L3 TI"
    }
}
