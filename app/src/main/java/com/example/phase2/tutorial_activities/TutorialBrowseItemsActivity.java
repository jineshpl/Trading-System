package com.example.phase2.tutorial_activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity class responsible for display items for a guest in the tutorial in
 * the Trading System.
 */
public class TutorialBrowseItemsActivity extends BundleActivity implements ClickableList {
    private ItemManager itemManager;
    private String itemInfo;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        setContentView(R.layout.activity_tutorial_browse_items);
        viewList();
    }

    /**
     * Called to set up the list, display item names and call the displayDialog
     * method when an item is selected.
     */
    public void viewList(){
        final List<Integer> itemIDs = itemManager.getAllApprovedItemsIDs("");
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        ListView listView = findViewById(R.id.itemsList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemInfo = itemManager.getItemInString(itemIDs.get(i));
                displayDialog();
            }
        });

    }

    /**
     * Called to display information of a selected item using a dialog.
     */
    public void displayDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Item Information")
                .setMessage(itemInfo);
        builder.show();
    }
}