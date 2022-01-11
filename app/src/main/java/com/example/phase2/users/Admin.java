package com.example.phase2.users;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class Admin extends User implements Serializable {

    private boolean isApproved;
    /**
     * Constructor for the admin class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
    public Admin(String username, String password) {
        super(username, password);
        isApproved = false;
    }

    /**
     * Alternative constructor for the Admin class used for reading in from a file.
     * @param username this User's username.
     * @param password this User's password.
     * @param date the date this User was created, in LocalDate format ("year-month-date")
     */
    public Admin(String username, String password, String date, boolean isApproved){
        super(username, password, date);
        this.isApproved = isApproved;
    }

    /**
     * Getter for isApproved
     * @return Return isApproved
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Setter for isApproved
     * @param approved whether or not the admin is approved
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * Converts the admin to a string representation.
     * @return a string of the admin's username, password, and date created
     */
    @NonNull
    @Override
    public String toString() {
        return "Admin{" +
                "username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", dateCreated=" + super.getDateCreated() +
                '}';
    }
}
