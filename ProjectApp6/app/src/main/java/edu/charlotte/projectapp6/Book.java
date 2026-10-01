/*
 * Assignment #6
 * File Name: Book.java
 * Full Name: Bryan Sandoval, Lucnel Nordelus
 */

package edu.charlotte.projectapp6;

import java.io.Serializable;

/**
 * Book model class representing an individual book item.
 * <p>
 * DEV NOTES:
 * - Implements Serializable: This is a crucial Android requirement if you want to pass 
 *   an instance of a custom object (like a Book) inside a Bundle (e.g., FragmentBookDetails.newInstance(book)).
 * - Encapsulation: Contains private fields with public getter and setter methods.
 */
public class Book implements Serializable {
    private String title;
    private String author;
    private int year;
    private String genre;

    public Book(String title, String author, int year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
