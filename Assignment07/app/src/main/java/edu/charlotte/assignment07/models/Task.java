/**
 * Assignment: 7
 * File Name: Task.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String id;
    private String name;
    private String category;
    private String priority;
    private String userId;

    public Task() {
    }

    public Task(String name, String category, String priority, String userId) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
