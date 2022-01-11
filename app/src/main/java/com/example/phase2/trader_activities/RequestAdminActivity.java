package com.example.phase2.trader_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

/**
 * An activity class responsible for request admin option
 */
public class RequestAdminActivity extends BundleActivity {
    private TraderManager traderManager;
    private ItemManager itemManager;
    private String currentTrader;

    /**
     * Sets up the activity class with all the necessary objects
     * @param savedInstanceState A bundle storing all the use case classes that are needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        currentTrader = getUsername();
        setContentView(R.layout.activity_request_admin);
    }

    /**
     * Changes the status of the user to inactive. Also updates all of the item's statuses.
     * @param view A view
     */
    public void requestToDeactivateAccount(View view){
        if(traderManager.getIsFrozen(currentTrader)){
            Toast.makeText(this, R.string.cannot_deactivate_frozen, Toast.LENGTH_SHORT).show();
        }
        else if(traderManager.isInactive(currentTrader)){
            Toast.makeText(this, R.string.Account_already_deactivated, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.Deactivation_request_sent, Toast.LENGTH_SHORT).show();
            traderManager.setTraderInactive(currentTrader, true);
            itemManager.setStatusForInactiveUser(currentTrader);
        }
    }

    /**
     * Changes the status of the user to active. Also updates all of the item's statuses.
     * @param view A view
     */
    public void requestToActivateAccount(View view){
        if(traderManager.getIsFrozen(currentTrader)){
            Toast.makeText(this, R.string.cannot_activate_frozen, Toast.LENGTH_SHORT).show();
        }
        else if(!traderManager.isInactive(currentTrader)){
            Toast.makeText(this, R.string.Account_already_activated, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.Activation_request_sent, Toast.LENGTH_SHORT).show();
            traderManager.setTraderInactive(currentTrader, false);
            itemManager.setStatusForRegularUser(currentTrader);
        }
    }

    /**
     * Sends unfreeze request to admins.
     * @param view A view
     */
    public void requestToUnfreeze(View view){
        if(traderManager.getIsFrozen(currentTrader)) {
            if(traderManager.getRequestToUnfreeze(currentTrader)){
                Toast.makeText(this, R.string.Trader_request_to_unfreeze_already_sent, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, R.string.Trader_request_to_unfreeze_sent, Toast.LENGTH_SHORT).show();
                traderManager.setRequestToUnfreeze(currentTrader, true);
            }
        }
        else{
            Toast.makeText(this, R.string.Trader_request_to_unfreeze_not_frozen, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the user presses the back button. Updates the use case classes.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replaceUseCase(traderManager);
        replaceUseCase(itemManager);
    }
}