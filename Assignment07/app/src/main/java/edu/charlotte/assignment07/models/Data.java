/**
 * Assignment: 7
 * File Name: Data.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07.models;

public class Data {
    public static final String[] categories = {
            "Work/Professional",
            "Personal Errands",
            "Home & Family",
            "Health & Fitness",
            "Finance",
            "Education & Learning",
            "Leisure & Social",
            "Projects",
            "Other"
    };

    public static final Priority[] priorities = {
            new Priority("Very High", 5),
            new Priority("High", 4),
            new Priority("Medium", 3),
            new Priority("Low", 2),
            new Priority("Very Low", 1)
    };
}
