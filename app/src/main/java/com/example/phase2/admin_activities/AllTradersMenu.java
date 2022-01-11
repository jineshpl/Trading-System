package com.example.phase2.admin_activities;

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

public class AllTradersMenu extends BundleActivity implements ClickableList, Dialogable {
    private TraderManager traderManager;
    private ItemManager itemManager;
    private String frozenTrader;


    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        viewList();

    }

    /**
     * listener for the back button, return to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    /**
     * view a list of traders that can be frozen
     */
    public void viewList(){
        final ArrayList<String> allTraders = traderManager.getTraders();
        setContentView(R.layout.activity_all_traders_menu);
        ListView listView = findViewById(R.id.freezeTrader);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allTraders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                frozenTrader = allTraders.get(i);
            }
        });
    }

    /**
     * freeze the account
     */
    @Override
    public void clickPositive() {
        if(traderManager.freezeAccount(frozenTrader)){
            itemManager.setStatusForFrozenUser(frozenTrader);
            traderManager.setTraderInactive(frozenTrader, false);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,
                    "Fail: the account is already frozen", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    /**
     * cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Opens the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Freeze")
                .show(getSupportFragmentManager(), "FreezeTrader");

    }
}