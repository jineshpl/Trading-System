package com.example.phase2.users;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class TraderManager extends Manager implements Serializable, Loginable {
    protected HashMap<String, Trader> users;
    private int weeklyLimit;
    private int maxInComplete;
    private int moreLend;

    /**
     * Constructor for TraderManager given a HashMap
     * @param users The hashmap of Traders to users.
     */
    public TraderManager(HashMap<String, Trader> users, int weeklyLimit, int maxInComplete, int moreLend){
        this.users = users;
        this.weeklyLimit = weeklyLimit;
        this.maxInComplete = maxInComplete;
        this.moreLend = moreLend;
    }


    /**
     * The login method that returns True if the given credentials match a user in the system, and false otherwise.
     * @param username The username of the user
     * @param password The password of the user
     * @return true if the credentials match, false otherwise.
     */
    public boolean login(String username, String password){
        if(users.containsKey(username)){
            return Objects.requireNonNull(users.get(username)).getPassword().equals(password);
        }
        return false;
    }

    /**
     * Returns a list of the usernames of all users that have been flagged.
     * @return list of the usernames of all users that have been flagged.
     */
    public List<String> getListOfFlagged(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> flaggedUser: this.users.entrySet()){
            if(flaggedUser.getValue().isFlagged()){
                temp.add(flaggedUser.getKey());
            }
        }
        return temp;
    }

    /**
     * @return a list of the usernames of all users that have requested to unfreeze their accounts.
     */
    public List<String> getAllRequestsToUnfreeze(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> unfreezeRequest: this.users.entrySet()){
            if(unfreezeRequest.getValue().isRequestToUnfreeze()){
                temp.add(unfreezeRequest.getKey());
            }
        }
        return temp;
    }

    /**
     * Check whether the username is valid or not
     * @param username the username that needs to be checked
     * @return Return true iff the username is valid
     */
    public boolean checkUsername(String username){
        return !users.containsKey(username);
    }

    /**
     * @return A list of strings of all usernames in the system.
     */
    public ArrayList<String> getTraders(){
        ArrayList<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> usernames: this.users.entrySet()){
            temp.add(usernames.getKey());
        }
        return temp;
    }

    /**
     * Gets the trader's string representation if the user is in the system.
     * @param username The username of the trader
     * @return a string of the trader's username, password, and date created or an empty string if the user does not exist with the program
     */
    public String getTraderInfo(String username){
        if (!checkUsername(username)){
            return Objects.requireNonNull(users.get(username)).toString();
        }
        else{
            return "";
        }
    }
    /**
     * Adds a new trader to the system
     * @param username The username of the trader
     * @param password The password of the trader
     */
    public void newTrader(String username, String password){
        users.put(username, new Trader(username, password));
    }

    /**
     * Adds a Trader object to users
     * @param t The trader object to add
     */
    public void addTrader(Trader t){
        if(containTrader(t.getUsername())){
            return;
        }
        users.put(t.getUsername(), t);
    }

    /**
     * Checks if the Trader with the given username exists in users
     * @param username Trader's username
     * @return True iff the Trader exists
     */
    public boolean containTrader(String username){
        return users.containsKey(username);
    }

    /**
     * Freezes a given account
     * @param username The account username that has to be frozen
     * @return Return true iff the account is successfully frozen. Return false
     * if the account is already frozen
     */
    public boolean freezeAccount(String username){
        Trader trader = users.get(username);
        if (Objects.requireNonNull(trader).isFrozen()){
            return false;
        }
        trader.setFrozen(true);
        trader.setFlagged(false);
        return true;
    }

    /**
     * Unfreezes a given account
     * @param username The account username that has to be unfrozen
     * @return Return true iff the account is successfully unfrozen. Return false
     * if the account is already unfrozen
     */
    public boolean unfreezeAccount(String username){
        Trader trader = users.get(username);
        assert trader != null;
        trader.setRequestToUnfreeze(false);
        if (!trader.isFrozen()){
            return false;
        }
        trader.setFrozen(false);
        trader.setFlagged(false);
        return true;
    }

    /**
     * Returns the IDs of the items in the inputted Trader's wishlist.
     * @param username the username of the Trader in question
     * @return a list of the IDs of the items in the inputted user's wishlist.
     */
    public List<Integer> getWishlistIds(String username) {
        return Objects.requireNonNull(users.get(username)).getWishlist();
    }

    /**
     * Removes an item from a Traders's wishlist.
     * @param username the username of the Trader in question
     * @param id the ID of the item in question
     */
    public void removeFromWishlist(String username, Integer id){
        Objects.requireNonNull(users.get(username)).getWishlist().remove(id);
    }

    /**
     * Adds an item to a Trader's wishlist.
     * @param username the username of the Trader in question
     * @param id the ID of the item in question
     */
    public void addToWishlist(String username, Integer id){
        Objects.requireNonNull(users.get(username)).getWishlist().add(id);
    }

    /**
     * Returns true iff the Trader with the given username is frozen.
     * @param username the username of the Trader in question
     * @return true, iff the Trader with the given username's account is frozen.
     */
    public boolean getIsFrozen(String username){
        return Objects.requireNonNull(users.get(username)).isFrozen();
    }

    /**
     * Returns true iff the Trader with the given username has requested to have their account
     * unfrozen.
     * @param username the username of the Trader in question
     * @return a boolean representing if the trader has an unfreeze request
     */
    public boolean getRequestToUnfreeze(String username){
        return Objects.requireNonNull(users.get(username)).isRequestToUnfreeze();
    }

    /**
     * Sets the Trader's requestToUnfreeze variable to the inputted boolean.
     * @param username the username of the Trader in question
     * @param setting the new state of the Trader's requestToUnfreeze variable.
     */
    public void setRequestToUnfreeze(String username, boolean setting){
        Objects.requireNonNull(users.get(username)).setRequestToUnfreeze(setting);
    }

    /**check whether or not the user exceed weekly trade limit
     * @param user the user who wants to create a new trade
     * @param createDate the date that the user requests to create the trade
     * @return whether or not the user exceed weekly trade limit
     */
    public boolean exceedWeeklyLimit(String user, LocalDate createDate){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = createDate.get(weekFields.weekOfWeekBasedYear());
        int n = 0;
        for(LocalDate date: Objects.requireNonNull(users.get(user)).getTrades().values()){
            if(date.getYear() == createDate.getYear()
                    && date.get(weekFields.weekOfWeekBasedYear()) == weekNumber){
                n++;
            }
        }
        return n >= weeklyLimit;
    }

    /**Check whether or not the user exceed the max number of incomplete trades
     * @param user the usr who wants to request to trade
     * @return whether or not the user exceed the max number of incomplete trades
     */
    public boolean exceedMaxIncomplete(String user){
        return Objects.requireNonNull(users.get(user)).getNumIncomplete() > maxInComplete;
    }


    /**check whether or not the user need to lend more items
     * @param user the user who wants to trade
     * @return whether or not the user need to lend more items
     */
    public boolean needMoreLend(String user){
        if(Objects.requireNonNull(users.get(user)).getTrades().isEmpty()){
            return false;
        }else{
            return Objects.requireNonNull(users.get(user)).getNumLent() - Objects.requireNonNull(users.get(user)).getNumBorrowed() < moreLend;
        }
    }

    /**Get a list of trades
     * @param user the user who wants to get the list of trades
     * @return the list of trades belonged to that user
     */
    public List<Integer> getTrades(String user){
        return new ArrayList<>(Objects.requireNonNull(users.get(user)).getTrades().keySet());
    }

    /**Add a new trade to the user's list of trades
     * @param user the user who wants to trade
     * @param id the id of the trade
     * @param createDate the created date for the trade
     */
    public void addNewTrade(String user, int id, LocalDate createDate){
        Objects.requireNonNull(users.get(user)).addTrades(id, createDate);
    }

    /**Increase the number of incomplete trades
     * @param user the user
     */
    public void increaseNumbIncomplete(String user){
        Objects.requireNonNull(users.get(user)).setNumIncomplete(Objects.requireNonNull(users.get(user)).getNumIncomplete() + 1);
    }

    /**Decrease the number of incomplete trades
     * @param user the user
     */
    public void decreaseNumIncomplete(String user){
        Objects.requireNonNull(users.get(user)).setNumIncomplete(Objects.requireNonNull(users.get(user)).getNumIncomplete() - 1);
    }


    /**Getter for weeklyLimit
     * @return the maximum number of trades a trade can have in a week
     */
    public int getWeeklyLimit() {
        return weeklyLimit;
    }

    /**Setter for weeklyLimit
     * @param weeklyLimit the maximum number of trades a trade can have in a week
     */
    public void setWeeklyLimit(int weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    /**Getter for maxInComplete
     * @return the max number of incomplete trades that a user could have
     */
    public int getMaxInComplete() {
        return maxInComplete;
    }

    /**Setter for maxInComplete
     * @param maxInComplete the max number of incomplete trades that a user could have
     */
    public void setMaxInComplete(int maxInComplete) {
        this.maxInComplete = maxInComplete;
    }

    /**Getter for moreLend
     * @return the number that the trader need to lend before they can borrow
     */
    public int getMoreLend() {
        return moreLend;
    }

    /**Setter for moreLend
     * @param moreLend the number that the trader need to lend before they can borrow
     */
    public void setMoreLend(int moreLend) {
        this.moreLend = moreLend;
    }

    /**
     * Sets username's status to active or inactive.
     * @param username The trader's username
     * @param bool whether the trader will be active or inactive
     */
    public void setTraderInactive(String username, boolean bool){
        Objects.requireNonNull(users.get(username)).setInactive(bool);
    }

    /**
     * Get's the user with the given user's activity status
     * @param username The user who's status is to be checked
     * @return true if username is inactive, false otherwise.
     */
    public boolean isInactive(String username){
        return Objects.requireNonNull(users.get(username)).getInactive();
    }

    /**
     * Changes the password of the Trader with the given username to new password
     * @param username Trader's username that is changing the password
     * @param newPassword New password
     */
    public void changePassword(String username, String newPassword){
        if(users.containsKey(username)) {
            Objects.requireNonNull(users.get(username)).setPassword(newPassword);
        }
    }

    /**
     * Gets the user's home city with the given username
     * @param username The user who's home city is to be get
     * @return the string representing the trader's home city
     */
    public String getHomeCity(String username){
        return Objects.requireNonNull(users.get(username)).getHomeCity();
    }

    /**
     * Sets the user's new home city with the given username and home city
     * @param username The user who's home city is to be changed
     * @param newHomeCity the string representing the trader's new home city
     */
    public void setHomeCity(String username, String newHomeCity){
         Objects.requireNonNull(users.get(username)).setHomeCity(newHomeCity);
    }

    /**
     * Gets the identifier for this class.
     * @return "TradeManager"
     */
    @Override
    public String getIdentifier() {
        return "TraderManager";
    }

    /**
     * Removes the trade with the given id from both traders' list of trades.
     * @param id The id of the trade
     * @param firstUser The first trader
     * @param secondUser The second trader
     */
    public void removeTradeFromTraders(int id, String firstUser,String secondUser){
        Objects.requireNonNull(users.get(firstUser)).removeTrade(id);
        Objects.requireNonNull(users.get(secondUser)).removeTrade(id);
    }

    /**
     * Flags the user with the given id.
     * @param username The username of the user
     */
    public void flagUser(String username){
        Objects.requireNonNull(users.get(username)).setFlagged(true);
    }

    /**
     * Unflags the given user
     * @param username The username of the user
     */
    public void unFlagUser(String username){
        Objects.requireNonNull(users.get(username)).setFlagged(false);
    }

    /**
     * Increases the user's numLent
     * @param username The username of the user
     */
    public void increaseNumLent(String username){
        Objects.requireNonNull(users.get(username)).setNumLent(Objects.requireNonNull(users.get(username)).getNumLent()+1);
    }

    /**
     * Increases the users numBorrowed.
     * @param username The username of the user
     */
    public void increaseNumBorrowed(String username){
        Objects.requireNonNull(users.get(username)).setNumBorrowed(Objects.requireNonNull(users.get(username)).getNumBorrowed()+1);
    }



}
