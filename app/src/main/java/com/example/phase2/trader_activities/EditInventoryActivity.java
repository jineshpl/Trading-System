package com.example.phase2.trader_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.menus.TraderActivity;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity class responsible for viewing items in an inventory in the Trading System.
 */
public class EditInventoryActivity extends BundleActivity implements ClickableList {
    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private int chosenItem;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();
        viewList();
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        replaceUseCase(itemManager);
        Intent intent = new Intent(this, TraderActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Called to set up the list, display item names and call the displayRemoveItem
     * method when an item is selected.
     */
    public void viewList(){
        final List<Integer> itemIDs = itemManager.getApprovedItemsIDs(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_edit_inventory);
        ListView listView = findViewById(R.id.inventoryList);
        ArrayAdapter<String> inventoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(inventoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenItem = itemIDs.get(i);
                displayRemoveItem();
            }
        });

    }

    /**
     * Called to remove item from system and displays it is successfully.
     */
    public void removeItem(){
        itemManager.removeItem(chosenItem);
        Toast.makeText(this, "Successfully removed the item",
                Toast.LENGTH_LONG).show();
        viewList();
    }

    /**
     * Called when the user clicks on an item. It creates a new dialog showing the
     * item information and options to remove the item or cancel.
     */
    public void displayRemoveItem(){
        String itemInfo = itemManager.getItemInString(chosenItem);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to remove the item?\n"+itemInfo)
                .setPositiveButton("Remove Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeItem();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        viewList();
                    }
                });
        builder.show();
    }

    /**
     * Called when the user clicks on the Add New Item button. It starts
     * the AddNewItemActivity
     * @param view A view
     */
    public void addNewItem(View view){
        if(traderManager.getIsFrozen(currentTrader)){
            Toast.makeText(this, R.string.cannot_propose_new_item_frozen, Toast.LENGTH_SHORT).show();
        }else if(traderManager.isInactive(currentTrader)){
            Toast.makeText(this, R.string.cannot_propose_new_item_inactive, Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, AddNewItemActivity.class);
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }
    }

}