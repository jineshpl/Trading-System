package com.example.phase2.highabstract;
import android.os.Bundle;

import com.example.phase2.items.Item;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.Meeting;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trades.Trade;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.users.Admin;
import com.example.phase2.users.AdminActions;
import com.example.phase2.users.Trader;
import com.example.phase2.users.TraderManager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConfigGateway {

    private final String ADMINPATH = "admins.ser";
    private String ITEMPATH = "items.ser";
    private String MEETINGPATH = "meetings.ser";
    private String TRADEPATH = "trade.ser";
    private String TRADERPATH = "traders.ser";

    private AdminActions adminActions;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;

    private File contextFilesDir;

    /**
     * Constructor for the ConfigGateway class
     * @param contextFilesDir the File containing the context files directory for the application
     */
    public ConfigGateway(File contextFilesDir){
        this.contextFilesDir = contextFilesDir;
    }

    /**
     * Reads objects from the given path
     * @param path the string path of the file
     * @return a Serializable object containing information for the program
     * @throws IOException from the InputStream
     * @throws ClassNotFoundException from ObjectInputStream
     */
    public Serializable readInfo(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            InputStream fileInput = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(fileInput);
            ObjectInput input = new ObjectInputStream(buffer);

            Serializable serializable = (Serializable) input.readObject();
            input.close();
            return serializable;
        } else {
            if (file.createNewFile()) {
                return readInfo(path);
            }
            else{
                return null;
            }
        }
    }

    private void loadClasses() throws IOException {
        try {
            adminActions = (AdminActions) readInfo(contextFilesDir + ADMINPATH);
            meetingManager = (MeetingManager) readInfo(contextFilesDir + MEETINGPATH);
            itemManager = (ItemManager) readInfo(contextFilesDir + ITEMPATH);
            tradeManager = (TradeManager) readInfo(contextFilesDir + TRADEPATH);
            traderManager = (TraderManager) readInfo(contextFilesDir + TRADERPATH);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            downloadInitial();
        }
    }

    /**
     * Packages the manager classes into a Bundle object
     * @return a Bundle object containing the Manager use case classes in Serializable form
     */
    public Bundle getBundle() throws IOException {
        loadClasses();
        Bundle bundle = new Bundle();
        bundle.putSerializable(traderManager.getIdentifier(), traderManager);
        bundle.putSerializable(itemManager.getIdentifier(), itemManager);
        bundle.putSerializable(tradeManager.getIdentifier(), tradeManager);
        bundle.putSerializable(meetingManager.getIdentifier(), meetingManager);
        bundle.putSerializable(adminActions.getIdentifier(), adminActions);

        return bundle;
    }

    /**
     * Saves the information to the file
     * @param path the string path to the file
     * @param manager the Serializable object containing information about the file
     * @throws IOException from the OutputStream
     */
    public void saveInfo(String path, Serializable manager) throws IOException{
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(manager);
        output.close();
    }

    /**
     * Writes the contents of the bundle to the phone's storage
     * @param bundle The Bundle of use case manager classes
     */
    public void saveBundle(Bundle bundle) throws IOException {
        saveInfo(contextFilesDir + ADMINPATH, bundle.getSerializable("AdminActions"));
        saveInfo(contextFilesDir + MEETINGPATH, bundle.getSerializable("MeetingManager"));
        saveInfo(contextFilesDir + ITEMPATH, bundle.getSerializable("ItemManager"));
        saveInfo(contextFilesDir + TRADEPATH, bundle.getSerializable("TradeManager"));
        saveInfo(contextFilesDir + TRADERPATH, bundle.getSerializable("TraderManager"));
    }

    private void downloadUsers(){
        HashMap<String, Admin> admins = new HashMap<>();
        admins.put("Admin", new Admin("Admin", "Wordpass", "2020-07-27", true));
        adminActions = new AdminActions(admins);

        traderManager = new TraderManager(new HashMap<String, Trader>(), 100, 1, 0);
        Trader trader1 = new Trader("Trader1", "Password");
        trader1.setHomeCity("Brampton");
        traderManager.addTrader(trader1);

        traderManager.addTrader(new Trader("Trader2", "Password2"));
        Trader traderFlagged = new Trader("Arjun", "Password3");
        traderFlagged.setHomeCity("Toronto");
        traderFlagged.setFlagged(true);

        traderManager.addTrader(traderFlagged);
        Trader traderUnfreeze = new Trader("Jeffrey", "Password4");
        traderUnfreeze.setFrozen(true);
        traderUnfreeze.setRequestToUnfreeze(true);
        traderManager.addTrader(traderUnfreeze);


        adminActions.newAdmin("Admin2", "Wordpass");
        adminActions.newAdmin("Sup", "nothing");
    }

    private void downloadItems(){
        HashMap<Integer, Item> tempMap = new HashMap<>();
        itemManager = new ItemManager(tempMap);

        Integer id1 = itemManager.addItem("Bruh", "Arjun");
        itemManager.editCategory(id1, "What do you think?");
        itemManager.editDescription(id1, "bruhbruhbruh");
        itemManager.editQualityRating(id1,5);
        itemManager.changeStatusToAvailable(id1);

        Integer id2 = itemManager.addItem("Apple", "Trader1");
        itemManager.editCategory(id2, "Food");
        itemManager.editDescription(id2, "It's an apple.");
        itemManager.editQualityRating(id2, 8);
        itemManager.changeStatusToAvailable(id2);
    }

    private void addFirstTrade(){
        meetingManager = new MeetingManager(new HashMap<Integer, Meeting>());
        tradeManager = new TradeManager(new HashMap<Integer, Trade>());
        List<Integer> tempTradeItems = new ArrayList<>();
        tempTradeItems.add(itemManager.addItem("Bike", "Arjun"));
        itemManager.addItemDetails(tempTradeItems.get(0), "Transportation", "Its a bike", 10);
        itemManager.changeStatusToUnavailable(tempTradeItems.get(0));
        Integer tradeId = tradeManager.createTrade("Arjun", "Trader2", "ONEWAY", true, tempTradeItems);
        LocalDate tempDate = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId,tempDate);
        traderManager.addNewTrade("Trader2", tradeId, tempDate);
        meetingManager.createMeeting(tradeId, "Arjun", "Trader2", true);
        meetingManager.setMeetingInfo(tradeId, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");
    }

    private void addSecondTrade(){
        List<Integer> tempTradeItems2 = new ArrayList<>();
        Integer temp2 = itemManager.addItem("Jacket", "Trader2");
        Integer temp3 = itemManager.addItem("Watch", "Arjun");
        tempTradeItems2.add(temp2);
        tempTradeItems2.add(temp3);
        itemManager.addItemDetails(temp2, "Clothing", "Its a jacket", 7);
        itemManager.addItemDetails(temp3, "Accessories", "Its a watch", 5);
        itemManager.changeStatusToUnavailable(temp2);
        itemManager.changeStatusToUnavailable(temp3);
        Integer tradeId2 = tradeManager.createTrade("Arjun", "Trader2", "TWOWAY", true, tempTradeItems2);
        LocalDate tempDate2 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId2,tempDate2);
        traderManager.addNewTrade("Trader2", tradeId2, tempDate2);
        meetingManager.createMeeting(tradeId2, "Arjun", "Trader2", true);
        meetingManager.setMeetingInfo(tradeId2, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");
    }

    private void addThirdTrade(){
        List<Integer> tempTradeItems3 = new ArrayList<>();
        tempTradeItems3.add(itemManager.addItem("Light", "Arjun"));
        itemManager.addItemDetails(tempTradeItems3.get(0), "Home", "Its a light", 10);
        itemManager.changeStatusToUnavailable(tempTradeItems3.get(0));
        Integer tradeId3 = tradeManager.createTrade("Arjun", "Trader1", "ONEWAY", false, tempTradeItems3);
        LocalDate tempDate3 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId3,tempDate3);
        traderManager.addNewTrade("Trader1", tradeId3, tempDate3);
        meetingManager.createMeeting(tradeId3, "Arjun", "Trader1", false);
        meetingManager.setMeetingInfo(tradeId3, LocalDate.now(), LocalDate.now(),
                "Toronto", "N/A");
    }

    private void addFourthTrade(){
        List<Integer> tempTradeItems4 = new ArrayList<>();
        Integer temp6 = itemManager.addItem("Laptop", "Trader1");
        Integer temp7 = itemManager.addItem("Phone", "Arjun");
        tempTradeItems4.add(temp6);
        tempTradeItems4.add(temp7);
        itemManager.addItemDetails(temp6, "Technology", "Its a laptop", 7);
        itemManager.addItemDetails(temp7, "Technology", "Its a Phone", 5);
        itemManager.changeStatusToUnavailable(temp6);
        itemManager.changeStatusToUnavailable(temp7);
        Integer tradeId4 = tradeManager.createTrade("Arjun", "Trader1", "TWOWAY", false, tempTradeItems4);
        LocalDate tempDate4 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId4,tempDate4);
        traderManager.addNewTrade("Trader1", tradeId4, tempDate4);
        meetingManager.createMeeting(tradeId4, "Arjun", "Trader1", false);
        meetingManager.setMeetingInfo(tradeId4, LocalDate.now(), LocalDate.now(),
                "Toronto", "N/A");
    }

    private void addFifthTrade(){
        List<Integer> tempTradeItems5 = new ArrayList<>();
        Integer temp8 = itemManager.addItem("E-Book", "Trader1");
        tempTradeItems5.add(temp8);
        itemManager.changeStatusToUnavailable(temp8);
        itemManager.addItemDetails(temp8, "Online", "Its an ebook", 10);
        Integer tradeId5 = tradeManager.createTrade("Arjun", "Trader1", "ONLINE-ONEWAY", true, tempTradeItems5);
        LocalDate tempDate5 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId5,tempDate5);
        traderManager.addNewTrade("Trader1", tradeId5, tempDate5);
        meetingManager.createMeeting(tradeId5, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId5, LocalDate.now(), LocalDate.now(),
                "Online", "Online");
    }

    private void addSixthTrade(){
        List<Integer> tempTradeItems6 = new ArrayList<>();
        Integer temp9 = itemManager.addItem("E-Book-2", "Trader1");
        Integer temp10 = itemManager.addItem("E-Textbook", "Arjun");
        tempTradeItems6.add(temp9);
        tempTradeItems6.add(temp10);
        itemManager.addItemDetails(temp9, "Online", "Its a ebook", 7);
        itemManager.addItemDetails(temp10, "Online", "Its a textbook", 5);
        itemManager.changeStatusToUnavailable(temp9);
        itemManager.changeStatusToUnavailable(temp10);
        Integer tradeId6 = tradeManager.createTrade("Arjun", "Trader1", "ONLINE-TWOWAY", true, tempTradeItems6);
        LocalDate tempDate6 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId6,tempDate6);
        traderManager.addNewTrade("Trader1", tradeId6, tempDate6);
        meetingManager.createMeeting(tradeId6, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId6, LocalDate.now(), LocalDate.now(),
                "ONLINE", "N/A");

    }
    private void downloadInitial() throws IOException {
        downloadUsers();
        downloadItems();
        addFirstTrade();
        addSecondTrade();
        addThirdTrade();
        addFourthTrade();
        addFifthTrade();
        addSixthTrade();

        Bundle bundle = new Bundle();
        bundle.putSerializable("AdminActions", adminActions);
        bundle.putSerializable("TraderManager", traderManager);
        bundle.putSerializable("MeetingManager", meetingManager);
        bundle.putSerializable("TradeManager", tradeManager);
        bundle.putSerializable("ItemManager", itemManager);
        saveBundle(bundle);
    }

}
