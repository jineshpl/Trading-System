package com.example.phase2.item_browsing_activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class SelectItemActivity extends BundleActivity implements Dialogable, ClickableList {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;
    private Integer myItem;
    private boolean oneWay;
    private boolean temporary;
    private boolean online;
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();
        chosenItem = bundle.getInt("ChosenItem");
        oneWay = bundle.getBoolean("OneWay");
        temporary = bundle.getBoolean("Temporary");
        online = bundle.getBoolean("Online");
        openDialog();
    }

    /**
     * Helper method for onDialogPositiveClick that returns the best item for the trade.
     * @return the ID of the best item for the trade.
     */
    private int bestItem() {
        String receiver = itemManager.getItemOwner(chosenItem);
        for(Integer receiverIDs : traderManager.getWishlistIds(receiver)){
            if(itemManager.getApprovedItemsIDs(currentTrader).contains(receiverIDs)){
                return receiverIDs;
            }
        }
        int ratingDifference = 11;
        int helper;
        int currentItem = -1;
        for(Integer i : itemManager.getApprovedItemsIDs(currentTrader)){
            helper = Math.abs(Integer.parseInt(itemManager.getItemQuality(i)) -
                    Integer.parseInt(itemManager.getItemQuality(chosenItem)));
            if (helper == 0) {
                currentItem = i;
                return currentItem;
            }
            else if (helper < ratingDifference) {
                currentItem = i;
                ratingDifference = helper;
            }
        }
        return currentItem;
    }

    /**
     * Updates the ListView object in the XML file
     */
    public void viewList(){
        final List<Integer> itemIDs = itemManager.getApprovedItemsIDs(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_select_item);
        ListView listView = findViewById(R.id.selectItem);
        ArrayAdapter<String> inventoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(inventoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myItem = itemIDs.get(i);
                continuing();
            }
        });
    }

    /**
     * Sends the user to the next activity
     */
    public void continuing() {
        Intent intent = new Intent(this, EnterInfoProposeTradeActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("MyItem", myItem);
        intent.putExtra("Temporary", temporary);
        intent.putExtra("OneWay", oneWay);
        intent.putExtra("Online", online);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Listener for positive button, show the item we recommend
     */
    @Override
    public void clickPositive() {
        myItem = bestItem();
        Toast.makeText(this, "We chose the item " + itemManager.getItemName(myItem) +
                " with ID " + myItem.toString(), Toast.LENGTH_LONG).show();
        continuing();
    }


    /**
     * Listener for negative button, goes to the list that allows user chooses himself
     */
    @Override
    public void clickNegative() {
        viewList();
    }

    /**
     * Opens the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Recommend")
                .show(getSupportFragmentManager(), "Recommend");

    }
}