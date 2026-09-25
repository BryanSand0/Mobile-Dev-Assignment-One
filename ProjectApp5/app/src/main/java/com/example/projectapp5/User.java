/*
 * Assignment #5
 * File Name: User.java
 * Full Name: Bryan Sandoval & Lucnel Nordelus
 */

package com.example.projectapp5;

import java.io.Serializable;

public class User implements Serializable {

    private final String name;
    private final String email;
    private final String role;
    private final String income;
    private final String status;

    public User(
            String name,
            String email,
            String role,
            String income,
            String status) {

        this.name = name;
        this.email = email;
        this.role = role;
        this.income = income;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getIncome() {
        return income;
    }

    public String getStatus() {
        return status;
    }
}