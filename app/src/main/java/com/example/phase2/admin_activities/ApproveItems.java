package com.example.phase2.admin_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.items.ItemManager;
import com.example.phase2.R;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;

import java.util.List;

import static com.example.phase2.items.ItemStatus.AVAILABLE;
import static com.example.phase2.items.ItemStatus.REMOVED;

public class ApproveItems extends BundleActivity implements ClickableList, Dialogable {
    private ItemManager itemManager;
    private Integer processedItem;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        viewList();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(itemManager);
        super.onBackPressed();
    }

    /**
     * Updates the ListView object in this Activity
     */
    public void viewList(){
        final List<Integer> itemIds = itemManager.getItemsNeedingApproval();
        List<String> items = itemManager.getListOfItemsInString(itemIds);
        setContentView(R.layout.activity_approve_items);
        ListView listView= findViewById(R.id.processedItems);
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * Callback method to be invoked when an item in this AdapterView has been clicked.
             * @param adapterView The AdapterView where the click happened
             * @param view The view within the AdapterView that was clicked (this will be a view provided by the adapter)
             * @param i The position of the view in the adapter.
             * @param l The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                processedItem = itemIds.get(i);
            }
        });
    }

    /**
     * Called when the positive button is clicked
     */
    @Override
    public void clickPositive() {
        if(itemManager.getItemStatus(processedItem) == REMOVED){
            Toast.makeText(this,
                    "fail: the item has been rejected", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.removeItem(processedItem);
            Toast.makeText(this,
                    "Successfully rejected!", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    /**
     * Called when the negative button is clicked
     */
    @Override
    public void clickNegative() {
        if(itemManager.getItemStatus(processedItem) == AVAILABLE){
            Toast.makeText(this,
                    "fail: the item has been approved", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.approveItem(processedItem, true);
            Toast.makeText(this,
                    "Successfully approved!", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    /**
     * Opens the Dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Approve")
                .show(getSupportFragmentManager(), "ApproveItem");

    }
}