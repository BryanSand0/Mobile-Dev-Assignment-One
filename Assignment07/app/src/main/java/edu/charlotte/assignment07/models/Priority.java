/**
 * Assignment 7
 * File Name: Priority.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 */
package edu.charlotte.assignment07.models;

public class Priority {
    String name;
    int level;

    public Priority(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Priority() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name;
    }
}
