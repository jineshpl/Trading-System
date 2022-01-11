package com.example.phase2.users;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private String username;
    private String password;
    private final String dateCreated;

    /**
     * Default constructor of User's subclasses.
     * @param username this User's username.
     * @param password this User's password.
     */
    public User (String username, String password){
        this.username = username;
        this.password = password;
        dateCreated = LocalDate.now().toString();
    }

    /**
     * Alternative constructor of User's subclasses, mainly used when reading in from a file.
     * @param username this User's username.
     * @param password this User's password.
     * @param date the date this User was created, in LocalDate format ("year-month-date")
     */
    public User (String username, String password, String date){
        this.username = username;
        this.password = password;
        this.dateCreated = date;
    }

    /**
     * Converts this User into String representation.
     * @return this User's String representation.
     */
    @NonNull
    @Override
    public abstract String toString();

    /**
     * Getter for username
     * @return this User's username
     */
    public String getUsername (){
        return username;
    }

    /**
     * Getter for password
     * @return this User's password
     */
    public String getPassword (){
        return password;
    }

    /**
     * Getter for dateCreated
     * @return this user's dateCreated (which is a LocalDate object)
     */
    public String getDateCreated (){
        return dateCreated;
    }

    /**
     * Setter for username
     * @param username this User's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for password
     * @param password this User's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
