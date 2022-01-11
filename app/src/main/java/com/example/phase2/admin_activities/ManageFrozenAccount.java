package com.example.phase2.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;

public class ManageFrozenAccount extends BundleActivity {
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_frozen_account);
    }

    /**
     * Goes to the flagged accounts menu
     * @param view The View object being clicked on
     */
    public void viewFlaggedAccounts(View view) {
        Intent intent = new Intent(this, FlaggedAccountsMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Goes to the unfreeze requests menus
     * @param view The View object being clicked on
     */
    public void viewUnfreezeRequests(View view){
        Intent intent = new Intent(this, RequestedUnfrozenMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Shows the all traders menu
     * @param view The View object being clicked on
     */
    public void viewAllTraders(View view){
        Intent intent = new Intent (this, AllTradersMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);

    }
}