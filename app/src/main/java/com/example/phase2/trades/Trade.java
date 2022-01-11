package com.example.phase2.trades;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Trade implements Serializable {
    private final String initiator;
    private final String receiver;
    private List<Integer> items;
    private final int id;
    private final boolean isPermanent;
    private boolean isCompleted;
    private final String tradeType;
    private final LocalDate createdDate;

    /**
     * Constructor for a new trade
     * @param id The id of the trade
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @param tradeType the trade type
     * @param isPermanent a boolean representing if the trade is permanent
     */
    public Trade(Integer id, String initiator, String receiver, String tradeType, boolean isPermanent){
        this.id = id;
        this.initiator = initiator;
        this.receiver = receiver;
        this.tradeType = tradeType;
        this.isPermanent = isPermanent;
        createdDate = LocalDate.now();
    }

    /**
     * A string representation of this trade
     * @return A string containing the details of the trade
     */
    @NonNull
    @Override
    public String toString(){

        return "-TradeID: " + getId() +
                "\n" +
                "-TradeType: " + getTradeType() +
                "\n" +
                "-Initiator: " + getInitiator() +
                "\n" +
                "-Receiver: " + getReceiver() +
                "\n" +
                "-TradeItems: " + getItems() +
                "\n" +
                "-IsPermanent: " + isPermanent() +
                "\n" +
                "-IsCompleted: " + getCompleted() +
                "\n" +
                "Trade is created on: " + getCreatedDate();
    }

    /**Getter for whether or not the trade is permanent
     * @return whether or not the trade is permanent
     */
    public boolean isPermanent() {
        return isPermanent;
    }


    /**
     * Getter for id
     * @return Id of the trade
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return List of item ids
     */
    public List<Integer> getItems() {
        return items;
    }

    /**
     * Setter of items
     * @param items List of item ids
     */
    public void setItems(List<Integer> items) {
        this.items = items;
    }

    /**Setter for isCompleted
     * @param isCompleted whether or not the trade is completed
     */
    public void setCompleted(Boolean isCompleted){this.isCompleted = isCompleted;}

    /**Getter for isCompleted
     * @return whether or not the trade is completed
     */
    public boolean getCompleted(){return this.isCompleted;}


    /**Getter for tradeType
     * @return the type of the trade
     */
    public String getTradeType(){return tradeType;}

    /**
     * Getter for initiator
     * @return the initiator of the trade
     */
    public String getInitiator() {
        return initiator;
    }

    /**
     * Getter for receiver
     * @return the receiver of the trade
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Getter for the date of the trade's creation
     * @return the date of the trade
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Returns the username of the other user in the trade.
     * @param username The username of one trader
     * @return The username of the other trader.
     */
    public String getOtherTrader(String username){
        if(initiator.equals(username)){
            return receiver;
        }
        else{
            return initiator;
        }
    }

}
