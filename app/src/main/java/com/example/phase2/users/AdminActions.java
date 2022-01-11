package com.example.phase2.users;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AdminActions extends Manager implements Serializable, Loginable {
    private final HashMap<String, Admin> admins;

    /**
     * Constructor for the AdminActions class
     * @param admins a HashMap of an admin's username to the Admin
     */
    public AdminActions(HashMap<String, Admin> admins){
        this.admins = admins;
    }

    /**
     * Adds a new admin request to the system
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public void newAdmin(String username, String password){
        admins.put(username, new Admin(username, password));
    }

    /**
     * Approves/rejects admins to the arraylist of admins
     * @param username the username of the admin that is being approved/rejected
     * @param approved a boolean representing whether or not the admin is approved
     */
    public void approveAdmin(String username, boolean approved){
        Objects.requireNonNull(admins.get(username)).setApproved(approved);
        if (!Objects.requireNonNull(admins.get(username)).isApproved()){
            admins.remove(username);
        }
    }

    /**
     * Check whether the username is valid or not
     * @param username the username that needs to be checked
     * @return Return true iff the username is valid
     */
    @Override
    public boolean checkUsername(String username){
        return !admins.containsKey(username);
    }

    /**
     * Verifies the login information for the admin
     * @param username the username of the login
     * @param password the password for the login
     * @return a boolean representing if the login was successful or not
     */
    public boolean login(String username, String password) {
        if (admins.containsKey(username)) {
            return Objects.requireNonNull(admins.get(username)).isApproved()
                    && Objects.requireNonNull(admins.get(username)).getPassword().equals(password);
        } else {
            return false;
        }
    }

    /**
     * Changes the password for this admin user's account
     * @param username the username for the admin
     * @param password the new password for the admin
     */
    public void changePassword(String username, String password){
        if (admins.containsKey(username)){
            Objects.requireNonNull(admins.get(username)).setPassword(password);
        }
    }

    /**
     * Gets list of requested admins
     * @return Return a list of requested admins
     */
    public ArrayList<String> getRequestedAdmins(){
        ArrayList<String> requestedAdmins = new ArrayList<>();
        for(String username: admins.keySet()){
            if(!Objects.requireNonNull(admins.get(username)).isApproved()){
                requestedAdmins.add(username);
            }
        }
        return requestedAdmins;
    }

    /**
     * Getter for the identifier of AdminActions
     * @return a String representing the identifier
     */
    @Override
    public String getIdentifier() {
        return "AdminActions";
    }
}
