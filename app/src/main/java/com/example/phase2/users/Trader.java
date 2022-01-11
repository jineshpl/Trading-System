package com.example.phase2.users;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trader extends User implements Serializable {
    private boolean frozen, flagged, requestToUnfreeze, inactive;
    private int numLent, numBorrowed, numIncomplete;
    private final List<Integer> wishlist;
    private HashMap<Integer, LocalDate> trades;
    private String homeCity;

    /**
     * This is a constructor for a new trader.
     * @param username The username of the trader
     * @param password The password of the trader.
     */
    public Trader(String username, String password){
        super(username, password);
        homeCity = "N/A";
        frozen = false;
        flagged = false;
        inactive=false;
        requestToUnfreeze = false;
        numLent = 0;
        numBorrowed = 0;
        numIncomplete=0;
        wishlist = new ArrayList<>();
        trades = new HashMap<>();
    }

    /**
     * Converts the trader to a string representation.
     * @return a string of the trader's username, password, and date created
     */
    @NonNull
    @Override
    public String toString(){
        String s = "Trader: "+ super.getUsername()+"\n";
        s += "Joined on: "+ super.getDateCreated()+"\n";
        s += "Frozen: "+frozen+" | Flagged: "+flagged+" | Request to Unfreeze: "+requestToUnfreeze+"\n\n";
        s += "Number of items borrowed: "+numBorrowed+" | Number of items lent: "+numLent;
        return s;
    }


    /**
     * Getter for user's frozen boolean
     * @return whether the account is frozen or not
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Setter for user's frozen boolean
     * @param frozen whether the account is frozen or not
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Getter for user's numLent
     * @return the number of items the user has lent
     */
    public int getNumLent() {
        return numLent;
    }

    /**
     * Setter for user's numLent
     * @param numLent the number of items this user has lent
     */
    public void setNumLent(int numLent) {
        this.numLent = numLent;
    }

    /**
     * Getter for user's numBorrowed
     * @return the number of items the user has borrowed
     */
    public int getNumBorrowed() {
        return numBorrowed;
    }

    /**
     * Setter for user's numBorrowed
     * @param numBorrowed the number of items this user has borrowed
     */
    public void setNumBorrowed(int numBorrowed) {
        this.numBorrowed = numBorrowed;
    }

    /**
     * Getter for user's flagged boolean
     * @return whether the user is flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Setter for user's flagged boolean
     * @param flagged whether the user is flagged
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Whether this user has requested to unfreeze or not.
     * @return true if they have requested to unfreeze, false otherwise.
     */
    public boolean isRequestToUnfreeze() {
        return requestToUnfreeze;
    }

    /**
     * Sets whether this user has requested to unfreeze.
     * @param requestToUnfreeze a boolean representing if the user has requested to unfreeze
     */
    public void setRequestToUnfreeze(boolean requestToUnfreeze) {
        this.requestToUnfreeze = requestToUnfreeze;
    }

    /**
     * Getter for user's wishlist
     * @return Trader's wishlist
     */
    public List<Integer> getWishlist() {
        return wishlist;
    }

    /**Getter for the numIncomplete
     * @return the number of incomplete trades that the trader has
     */
    public int getNumIncomplete() {
        return numIncomplete;
    }

    /**Setter for the numIncomplete
     * @param numIncomplete the number of incomplete trades that the trader has
     */
    public void setNumIncomplete(int numIncomplete) {
        this.numIncomplete = numIncomplete;
    }

    /**Getter for the trades
     * @return a hashmap recording trades that the trader has
     */
    public HashMap<Integer, LocalDate> getTrades() {
        return trades;
    }

    /**Add a trade to the trader's trade list
     * @param id the id of the trade
     * @param createdDate the date that the trade is created
     */
    public void addTrades(int id, LocalDate createdDate){
        if(trades.containsKey(id)){
            trades.replace(id, createdDate);
        }else{
            trades.put(id, createdDate);
        }
    }

    /**
     * Removes the given trade from the user.
     * @param id the id of the trade
     */
    public void removeTrade(int id){
        trades.remove(id);
    }

    /**
     * Setter for Trader's inactive.
     * @param inactive a boolean representing if the Trader is inactive
     */
    public void setInactive(boolean inactive){
        this.inactive = inactive;
    }

    /**
     * Returns whether the Trader is active or inactive
     * @return true if inactive, false otherwise
     */
    public boolean getInactive(){
        return inactive;
    }

    /**
     * returns the trader's current home city
     * @return the string representing the trader's home city
     */
    public String getHomeCity(){return homeCity;}

    /**
     * Setter for Trader's home city.
     * @param newHomeCity the string representing the new home city
     */
    public void setHomeCity(String newHomeCity){homeCity = newHomeCity;}
}
