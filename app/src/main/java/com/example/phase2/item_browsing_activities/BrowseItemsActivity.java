package com.example.phase2.item_browsing_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.highabstract.UpdatableBundleActivity;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class BrowseItemsActivity extends UpdatableBundleActivity implements ClickableList, Dialogable {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private int chosenItem;
    private boolean useLocation;

    /**
     * Updates the useCases
     */
    protected void updateUseCases(){
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
    }

    /**create the activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        currentTrader = getUsername();
        updateUseCases();
        if (traderManager.getHomeCity(currentTrader).equals(getString(R.string.notApplicable))) {
            useLocation = false;
            viewList();
        } else {
            openDialog();
        }
    }

    /**
     * view a list of items in the inventory
     */
    public void viewList(){
        List<Integer> tempItemList = itemManager.getAllApprovedItemsIDs(currentTrader);
        List<Integer> removeList = new ArrayList<>();
        if (useLocation){
            String location = traderManager.getHomeCity(currentTrader);
            for (Integer i: tempItemList){
                if(!traderManager.getHomeCity(itemManager.getOwner(i)).equals(location)){
                    removeList.add(i);
                }
            }
            for (Integer i: removeList){
                tempItemList.remove(i);
            }
        }
        final List<Integer> itemList = tempItemList;
        List<String> itemNameList = new ArrayList<>();
        for (Integer item : itemList) {
            itemNameList.add(itemManager.getItemName(item));
        }
        setContentView(R.layout.activity_browse_items);
        ListView listView = findViewById(R.id.selectItem);
        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNameList);
        listView.setAdapter(allItemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenItem = itemList.get(i);
                displayItemOptions();
            }
        });
    }

    /**
     * Listener for the back button, go back to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    /**
     * Continues to the next activity.
     */
    private void displayItemOptions(){
        Intent intent = new Intent(this, ItemOptionsActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }


    /**
     * Listener for the positive button, view the items from the home city
     */
    @Override
    public void clickPositive() {
        useLocation = true;
        viewList();
    }

    /**
     * Listener for the negative button, view the items from the world
     */
    @Override
    public void clickNegative() {
        useLocation = false;
        viewList();
    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("LocationChoice")
                .show(getSupportFragmentManager(), "LocationChoice");

    }
}
