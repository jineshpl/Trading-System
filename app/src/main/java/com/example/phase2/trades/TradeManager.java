package com.example.phase2.trades;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class TradeManager extends Manager implements Serializable {
    private final HashMap<Integer, Trade> tradeInventory;
    private int counter;

    /**
     * Constructor for the TradeManager use case class
     * @param tradeInventory a HashMap representing the inventory of trades in the system
     */
    public TradeManager(HashMap<Integer, Trade> tradeInventory){
        this.tradeInventory = tradeInventory;
        if (tradeInventory.keySet().size() != 0) {
            counter = Collections.max(tradeInventory.keySet()) + 1;
        }
        else{
            counter = 0;
        }
    }

    /**
     * Returns a list of username's incomplete trades.
     * @param trades a list of trades that user has (represented in Ids)
     * @return A list of the user's incomplete trades
     */
    public List<Integer> getIncompleteTrades(List<Integer> trades){
        List<Integer> temp = new ArrayList<>();
        for(Integer i: trades){
            if(!Objects.requireNonNull(tradeInventory.get(i)).getCompleted()){
                temp.add(i);
            }
        }
        return temp;
    }


    /**create a trade
     * @param isPermanent whether or not the trade is permanent
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @return the trade whether or not the trade is successfully created
     */
    public Integer createTrade(String initiator, String receiver, String tradeType, boolean isPermanent,
                           List<Integer> items){
        Trade trade = new Trade(counter, initiator, receiver, tradeType, isPermanent);
        trade.setCompleted(false);
        trade.setItems(items);
        counter++;
        addToTradeInventory(trade);
        return trade.getId();
    }

    /**add a trade to the tradeInventory
     * @param trade the trade needed to be added in inventory
     */
    public void addToTradeInventory(Trade trade){
        if(!tradeInventory.containsKey(trade.getId())){
            tradeInventory.put(trade.getId(), trade);
        }
    }

    /**remove a trade from tradeInventory
     * @param id the id of the trade
     */
    public void removeFromInventory(Integer id){
        tradeInventory.remove(id);
    }

    /**set the assigned trade with id to be completed
     * @param id the id of the trade
     */
    public void setTradeCompleted(int id){
        Objects.requireNonNull(tradeInventory.get(id)).setCompleted(true);
    }

    /**return the given trade's tradeType
     * @param id the id of the trade
     * @return which type the trade is(OneWay or TwoWay)
     */
    public String getTradeType(int id){return Objects.requireNonNull(tradeInventory.get(id)).getTradeType();}

    /**
     * Returns the given trade's dateCreated.
     * @param id the id of the trade in question.
     * @return the date the trade was completed.
     */
    public LocalDate getDateCreated(int id){return Objects.requireNonNull(tradeInventory.get(id)).getCreatedDate();}

    /**
     * Returns the items in the given trade.
     * @param id the id of the trade in question.
     * @return the list of ids corresponding to the items in the trade.
     */
    public List<Integer> getItems(int id){return Objects.requireNonNull(tradeInventory.get(id)).getItems();}

    /**
     * Method to check if the given trade id is permanent.
     * @param id The id of the trade
     * @return true iff trade is permanent.
     */
    public boolean isTradePermanent(int id){
        return Objects.requireNonNull(tradeInventory.get(id)).isPermanent();
    }

    /**
     * Returns the other trader in the trade with the given id
     * @param id The trade id
     * @param username The trader's username
     * @return The other trader's username
     */
    public String getOtherTrader(int id, String username){
        return Objects.requireNonNull(tradeInventory.get(id)).getOtherTrader(username);
    }

    /**
     * Getter for the identifier of TradeManager
     * @return a String representing the identifier
     */
    @Override
    public String getIdentifier() {
        return "TradeManager";
    }
}
