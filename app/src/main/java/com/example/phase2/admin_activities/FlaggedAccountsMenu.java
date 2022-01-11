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

import java.util.List;

public class FlaggedAccountsMenu extends BundleActivity implements ClickableList, Dialogable {
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
        setContentView(R.layout.activity_flagged_accounts_menu);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        viewList();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    /**
     * view a list of flagged accounts that needed to be frozen
     */
    public void viewList() {
        final List<String> allFlaggedTraders = traderManager.getListOfFlagged();
        ListView listView = findViewById(R.id.flagged);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allFlaggedTraders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                frozenTrader = allFlaggedTraders.get(i);
            }
        });
    }


    /**
     * Listener fot the positive button, freeze the account
     */
    @Override
    public void clickPositive() {
        if (traderManager.freezeAccount(frozenTrader)) {
            traderManager.setTraderInactive(frozenTrader, false);
            itemManager.setStatusForFrozenUser(frozenTrader);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Fail: the account is already frozen", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    /**
     * Listener for the negative button, cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Opens the Dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Freeze")
                .show(getSupportFragmentManager(), "FreezeTrader");
    }
}

