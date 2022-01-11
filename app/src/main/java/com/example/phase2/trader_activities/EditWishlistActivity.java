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
 * An activity class responsible for viewing items in a wishlist in the Trading System.
 */
public class EditWishlistActivity extends BundleActivity implements ClickableList {
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
        currentTrader = (String) getUseCase(USERNAMEKEY);
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
    public void viewList() {
        final List<Integer> itemIDs = traderManager.getWishlistIds(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id : itemIDs) {
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_edit_wishlist);
        ListView listView = findViewById(R.id.wishList);
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
     * Called to remove item from the trader's wish list and displays
     * it is successfully.
     */
    public void removeItem(){
        traderManager.removeFromWishlist(currentTrader, chosenItem);
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
}