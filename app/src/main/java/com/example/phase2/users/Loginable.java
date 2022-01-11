package com.example.phase2.users;

public interface Loginable {

    /**
     * Check whether the username is valid or not
     * @param username the username that needs to be checked
     * @return Return true iff the username is valid
     */
    boolean checkUsername(String username);

    /**
     * The login method that returns True if the given credentials match a user in the system, and false otherwise.
     * @param username The username of the user
     * @param password The password of the user
     * @return true if the credentials match, false otherwise.
     */
    boolean login(String username, String password);

    /**
     * Changes the password of the User with the given username to new password
     * @param username User's username that is changing the password
     * @param password The new password for the User
     */
    void changePassword(String username, String password);

}
