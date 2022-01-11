package com.example.phase2.meetings;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
public class Meeting implements Serializable {
    private final int tradeId;
    private LocalDate tradeDate;
    private LocalDate lastTradeDate;
    private String location;
    private String lastLocation;
    private String tradeStatus;
    private String returnLocation;
    private String lastReturnLocation;
    private LocalDate returnDate;
    private LocalDate lastReturnDate;
    private HashMap<String, Integer> numberOfEdits;
    private HashMap<String, Boolean> isAgreed;
    private HashMap<String, Boolean> isConfirmed;
    private HashMap<String, Boolean> isReturned;
    private boolean isPermanent;
    private boolean canBeUndone;

    /**
     * Constructor for the meeting between two individuals that are conducting a trade
     * @param tradeId The id of the trade
     */
    public Meeting(int tradeId){
        this.tradeId = tradeId;
        isConfirmed = new HashMap<>();
        numberOfEdits = new HashMap<>();
        isAgreed = new HashMap<>();
        this.tradeStatus = "In Progress";
        numberOfEdits = new HashMap<>();
        isAgreed = new HashMap<>();
        isConfirmed = new HashMap<>();
        isReturned = new HashMap<>();
        returnDate = LocalDate.of(0,1,1);
        returnLocation = "N/A";
        lastTradeDate = null;
        lastLocation = null;
        lastReturnLocation = null;
        canBeUndone = false;
    }

    /**
     * String representation of this meeting
     * @return a string representation of this meeting's details
     */
    @Override
    @NonNull
    public String toString(){
        StringBuilder s = new StringBuilder("-TradeID: " + getTradeId() +
                "\n" +
                "-MeetingDate: " + getTradeDate() +
                "\n" +
                "-MeetingLocation: " + getLocation() +
                "\n" +
                "-ReturnDate: " + getReturnDate() +
                "\n" +
                "-ReturnLocation: " + getReturnLocation() +
                "\n" +
                "-TradeStatus: " + getTradeStatus() +
                "\n");
        for(String trader: numberOfEdits.keySet()){
            s.append("-").append(trader).append("EditTimes: ").append(getNumberOfEdits().get(trader)).append("\n");
        }

        return s.toString();
    }

    /**Getter for the tradeDate
     * @return the date that the trade
     */
    public LocalDate getTradeDate(){return this.tradeDate;}

    /**Setter for the tradeDate
     * @param tradeDate the date that the trade will take place
     */
    public void setTradeDate(LocalDate tradeDate){
        lastTradeDate = this.tradeDate;
        this.tradeDate = tradeDate;
        canBeUndone = true;
    }

    /**
     * Getter for the last trade date of the meeting.
     * @return The last trade date of the meeting.
     */
    public LocalDate getLastTradeDate() {
        return lastTradeDate;
    }

    /**
     * Getter for the location of the meeting
     * @return a string representing the meeting location
     */
    public String getLocation(){return this.location;}

    /**Setter for the location
     * @param location the location that the trade may take place
     */
    public void setLocation (String location){
        lastLocation = this.location;
        this.location = location;
        canBeUndone = true;
    }

    /**
     * Getter for the last set location of the meeting
     * @return A string representing the last set location of the meeting
     */
    public String getLastLocation() {
        return lastLocation;
    }

    /**
     * Resets all the last recorded information for the meeting. Only to be used after undoing
     * changes to a meeting.
     */
    public void resetLastInfo() {
        lastTradeDate = null;
        lastLocation = null;
        lastReturnDate = null;
        lastReturnLocation = null;
        canBeUndone = false;
    }

    /**Getter for the trade status
     * @return the status for the trade
     */
    public String getTradeStatus(){return this.tradeStatus;}

    /**Setter for the trade status
     * @param tradeStatus the status of the trade
     */
    public void setTradeStatus(String tradeStatus){this.tradeStatus = tradeStatus;}


    /**Getter for isAgreed
     * @return a hashmap storing whether or not both trader agree the meeting
     */
    public HashMap<String, Boolean> getIsAgreed() {
        return isAgreed;
    }

    /**Getter for isConfirmed
     * @return a hashmap storing whether or not both trader confirm the trade
     */
    public HashMap<String, Boolean> getIsConfirmed() {
        return isConfirmed;
    }


    /**Getter for numberOfEdits
     * @return a hashmap storing the number of times each trader edits the meeting information
     */
    public HashMap<String, Integer> getNumberOfEdits() {
        return numberOfEdits;
    }

    /**
     * Setter for the number of edits
     * @param numberOfEdits a hashmap representing the number of edits each user has made
     */
    public void setNumberOfEdits(HashMap<String, Integer> numberOfEdits) {this.numberOfEdits = numberOfEdits;}

    /**
     * Setter for whether the traders have agreed
     * @param isAgreed a hashmap representing if each user has agreed
     */
    public void setIsAgreed(HashMap<String, Boolean> isAgreed){this.isAgreed = isAgreed;}

    /**increase the number of edits when the user edits the meeting
     * @param user the user who edit this meeting
     */
    @SuppressWarnings("ConstantConditions")
    public void increaseNumberOfEdits(String user) {
        numberOfEdits.replace(user, numberOfEdits.get(user) + 1);
    }

    /**
     * Decreases the number of edits when an admin undoes a chnage to the meeting.
     * @param user the user who last edited the meeting.
     */
    @SuppressWarnings("ConstantConditions")
    public void decreaseNumberOfEdits(String user) {
        numberOfEdits.replace(user, numberOfEdits.get(user) - 1);
    }

    /**allow the trader to agree the meeting
     * @param trader the trader who wants to agree the trade meeting
     * @param agree the boolean showing whether or not the trader agree this meeting
     */
    public void setAgree(String trader, Boolean agree) {
        if(isAgreed.containsKey(trader)){
            isAgreed.replace(trader, agree);
        }else{
            isAgreed.put(trader, agree);
        }
    }

    /**allow the trader to confirm the meeting
     * @param trader the trader who wants to confirm this trade
     * @param confirm the boolean showing whether or not the trader confirm this trade
     */
    public void setConfirm(String trader, boolean confirm) {
        if(isConfirmed.containsKey(trader)){
            isConfirmed.replace(trader, confirm);
        }else{
            isConfirmed.put(trader, confirm);
        }
    }

    /**
     *Getter for trade id
     * @return Trade Id
     */
    public int getTradeId() {
        return tradeId;
    }


    /**Getter for returnLocation
     * @return the returnMeeting's location
     */
    public String getReturnLocation() {
        return returnLocation;
    }

    /**Setter for returnLocation
     * @param returnLocation the return meeting's location
     */
    public void setReturnLocation(String returnLocation) {
        lastReturnLocation = this.returnLocation;
        this.returnLocation = returnLocation;
    }

    /**
     * Getter for lastReturnLocation
     * @return the Meeting's last return location
     */
    public String getLastReturnLocation() {
        return lastReturnLocation;
    }

    /**Getter for returnDate
     * @return the return meeting's date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**Setter for the return meeting
     * @param returnDate the return meeting's date
     */
    public void setReturnDate(LocalDate returnDate) {
        this.lastReturnDate = this.returnDate;
        this.returnDate = returnDate;
    }

    /**
     * Getter for the last return date.
     * @return This meeting's last return date.
     */
    public LocalDate getLastReturnDate() {
        return lastReturnDate;
    }

    /**
     * Getter for if the meeting is permanent or not
     * @return a boolean representing if the meeting is permanent
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**
     * A setter for whether the meeting is permanent
     * @param permanent A boolean representing if the meeting is parmanent
     */
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }


    /**
     * A setter for the hashmap representing if the items are returned
     * @param trader the username of the trader involved in the meeting
     * @param isReturn a boolean representing if the trader has returned their item
     */
    public void setReturn(String trader, Boolean isReturn){
        if(isReturned.containsKey(trader)){
            isReturned.replace(trader, isReturn);
        }else{
            isReturned.put(trader, isReturn);
        }
    }

    /**
     * Returns whether or not one can undo the last edit made to this meeting.
     * @return true, iff the meeting has an edit that can be undone.
     */
    public boolean getCanBeUndone(){
        return canBeUndone;
    }

    /**
     * Checks whether the meeting has been edited.
     * @return true if an edit has been made, false otherwise.
     */
    public boolean isEdited(){
        for(Integer i:numberOfEdits.values()){
            if(i!=0){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the user has agreed to the given meeting
     * @param username The username of the user
     * @return true if agreed, false otherwise
     */
    @SuppressWarnings("ConstantConditions")
    public boolean isAgreed(String username){
        return isAgreed.get(username);
    }

    /**
     * Returns whether the user with the given username has agreed to this meeting.
     * @param username The user's username
     * @return true iff they have agreed.
     */
    @SuppressWarnings("ConstantConditions")
    public boolean hasConfirmed(String username){
        if(isConfirmed.containsKey(username)){
            return isConfirmed.get(username);
        }
        return false;
    }

    /**
     * Sets both user's confirmations to the given boolean value.
     * @param b the boolean
     */
    public void setBothConfirm(boolean b){
        for(String s:isConfirmed.keySet()){
            isConfirmed.replace(s, b);
        }
    }

    /**
     * Makes both users in this meeting disagree with the meeting.
     */
    public void bothDisagree(){
        for(String user: isAgreed.keySet()){
            isAgreed.replace(user,false);
        }
    }

}
